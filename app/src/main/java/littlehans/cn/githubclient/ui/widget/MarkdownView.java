package littlehans.cn.githubclient.ui.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Base64;
import android.util.Log;
import android.webkit.ValueCallback;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by LittleHans on 2016/11/3.
 */

public class MarkdownView extends WebView {
  private static final String TAG = MarkdownView.class.getSimpleName();
  private static final String IMAGE_PATTERN = "!\\[(.*)\\]\\((.*)\\)";
  private String mPreviewText;

  public MarkdownView(Context context) {
    this(context, (AttributeSet) null);
  }

  public MarkdownView(Context context, AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public MarkdownView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    this.initialize();
  }

  @SuppressLint({ "SetJavaScriptEnabled" }) private void initialize() {
    this.setWebViewClient(new WebViewClient() {
      public void onPageFinished(WebView view, String url) {
        if (Build.VERSION.SDK_INT < 19) {
          MarkdownView.this.loadUrl(MarkdownView.this.mPreviewText);
        } else {
          MarkdownView.this.evaluateJavascript(MarkdownView.this.mPreviewText,
              (ValueCallback) null);
        }
      }
    });
    this.loadUrl("file:///android_asset/html/preview.html");
    this.getSettings().setJavaScriptEnabled(true);
    if (Build.VERSION.SDK_INT >= 16) {
      this.getSettings().setAllowUniversalAccessFromFileURLs(true);
    }

    if (Build.VERSION.SDK_INT >= 21) {
      this.getSettings().setMixedContentMode(0);
    }
  }

  public void loadMarkdownFromFile(File markdownFile) {
    String mdText = "";

    try {
      FileInputStream e = new FileInputStream(markdownFile);
      InputStreamReader inputStreamReader = new InputStreamReader(e);
      BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
      StringBuilder stringBuilder = new StringBuilder();

      String readText;
      while ((readText = bufferedReader.readLine()) != null) {
        stringBuilder.append(readText);
        stringBuilder.append("\n");
      }

      e.close();
      mdText = stringBuilder.toString();
    } catch (FileNotFoundException var8) {
      Log.e(TAG, "FileNotFoundException:" + var8);
    } catch (IOException var9) {
      Log.e(TAG, "IOException:" + var9);
    }

    this.setMarkDownText(mdText);
  }

  public void loadMarkdownFromAssets(String assetsFilePath) {
    try {
      StringBuilder e = new StringBuilder();
      InputStream json = this.getContext().getAssets().open(assetsFilePath);
      BufferedReader in = new BufferedReader(new InputStreamReader(json, "UTF-8"));

      String str;
      while ((str = in.readLine()) != null) {
        e.append(str).append("\n");
      }

      in.close();
      this.setMarkDownText(e.toString());
    } catch (IOException var6) {
      var6.printStackTrace();
    }
  }

  public void setMarkDownText(String markdownText) {
    String bs64MdText = this.imgToBase64(markdownText);
    String escMdText = this.escapeForText(bs64MdText);
    if (Build.VERSION.SDK_INT < 19) {
      this.mPreviewText = String.format("javascript:preview(\'%s\')", new Object[] { escMdText });
    } else {
      this.mPreviewText = String.format("preview(\'%s\')", new Object[] { escMdText });
    }
    initialize();
  }

  private String escapeForText(String mdText) {
    String escText = mdText.replace("\n", "\\\\n");
    escText = escText.replace("\'", "\\\'");
    escText = escText.replace("\r", "");
    return escText;
  }

  private String imgToBase64(String mdText) {
    Pattern ptn = Pattern.compile("!\\[(.*)\\]\\((.*)\\)");
    Matcher matcher = ptn.matcher(mdText);
    if (!matcher.find()) {
      return mdText;
    } else {
      String imgPath = matcher.group(2);
      if (!this.isUrlPrefix(imgPath) && this.isPathExCheck(imgPath)) {
        String baseType = this.imgEx2BaseType(imgPath);
        if (baseType.equals("")) {
          return mdText;
        } else {
          File file = new File(imgPath);
          byte[] bytes = new byte[(int) file.length()];

          try {
            BufferedInputStream base64Img = new BufferedInputStream(new FileInputStream(file));
            base64Img.read(bytes, 0, bytes.length);
            base64Img.close();
          } catch (FileNotFoundException var9) {
            Log.e(TAG, "FileNotFoundException:" + var9);
          } catch (IOException var10) {
            Log.e(TAG, "IOException:" + var10);
          }

          String base64Img1 = baseType + Base64.encodeToString(bytes, 2);
          return mdText.replace(imgPath, base64Img1);
        }
      } else {
        return mdText;
      }
    }
  }

  private boolean isUrlPrefix(String text) {
    return text.startsWith("http://") || text.startsWith("https://");
  }

  private boolean isPathExCheck(String text) {
    return text.endsWith(".png")
        || text.endsWith(".jpg")
        || text.endsWith(".jpeg")
        || text.endsWith(".gif");
  }

  private String imgEx2BaseType(String text) {
    return text.endsWith(".png") ? "data:image/png;base64,"
        : (!text.endsWith(".jpg") && !text.endsWith(".jpeg") ? (text.endsWith(".gif")
            ? "data:image/gif;base64," : "") : "data:image/jpg;base64,");
  }
}
