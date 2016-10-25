package littlehans.cn.githubclient.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import butterknife.BindView;
import com.mukesh.MarkdownView;
import littlehans.cn.githubclient.R;

/**
 * Created by LittleHans on 2016/10/25.
 */

public class FileDetailActivity extends BaseActivity {
  private static final String TAG = "FileDetailActivity";
  @BindView(R.id.markdown_view) MarkdownView mMarkdownView;


  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {

    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_file_detail);
    Intent intent = getIntent();
    String content = intent.getStringExtra("content");
    mMarkdownView.setMarkDownText(content);
  }

}
