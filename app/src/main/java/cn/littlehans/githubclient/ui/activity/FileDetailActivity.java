package cn.littlehans.githubclient.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import butterknife.Bind;
import cn.littlehans.githubclient.R;
import com.mukesh.MarkdownView;
import thereisnospon.codeview.CodeView;
import thereisnospon.codeview.CodeViewTheme;

/**
 * Created by LittleHans on 2016/10/25.
 */

public class FileDetailActivity extends BaseActivity {
  private static final String TAG = "FileDetailActivity";
  @Bind(R.id.code_view) CodeView mCodeView;
  @Bind(R.id.markdown_view) MarkdownView mMarkdownView;

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {

    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_file_detail);
    Intent intent = getIntent();
    String content = intent.getStringExtra("content");
    mMarkdownView.setOpenUrlInBrowser(true);
    boolean isMarkDownFile = intent.getBooleanExtra("isMarkDownFile", false);
    if (isMarkDownFile) {
      mCodeView.setVisibility(View.GONE);
      mMarkdownView.setVisibility(View.VISIBLE);
      mMarkdownView.setMarkDownText(content);
    } else {
      mCodeView.setTheme(CodeViewTheme.ANDROIDSTUDIO).fillColor();
      mCodeView.showCode(content);
    }

  }
}
