package littlehans.cn.githubclient.feature.search;

import android.text.TextUtils;

/**
 * Created by littlehans on 2016/10/7.
 */

public class PageLink {

  private static final String DELIM_LINKS = ","; //$NON-NLS-1$

  private static final String DELIM_LINK_PARAM = ";"; //$NON-NLS-1$

  private String mFirst;
  private String mLast;
  private String mNext;
  private String mPrev;

  /**
   * https://api.github.com/search/repositories?q=bootstrap&page=2
   * https://github.com/eclipse/egit-github/blob/master/org.eclipse.egit.github.core/src/org/eclipse/egit/github/core/client/PageLinks.java#L43-75
   */
  public PageLink(){}


  public PageLink(String linkHeads) {
    String linkHeader = linkHeads;
    if (linkHeader != null) {
      String[] links = linkHeader.split(DELIM_LINKS);
      for (String link : links) {
        String[] segments = link.split(DELIM_LINK_PARAM);
        if (segments.length < 2) continue;

        String linkPart = segments[0].trim();
        if (!linkPart.startsWith("<") || !linkPart.endsWith(">")) //$NON-NLS-1$ //$NON-NLS-2$
        {
          continue;
        }
        linkPart = linkPart.substring(1, linkPart.length() - 1);

        for (int i = 1; i < segments.length; i++) {
          String[] rel = segments[i].trim().split("="); //$NON-NLS-1$
          if (rel.length < 2 || !"rel".equals(rel[0])) continue;

          String relValue = rel[1];
          if (relValue.startsWith("\"") && relValue.endsWith("\"")) //$NON-NLS-1$ //$NON-NLS-2$
          {
            relValue = relValue.substring(1, relValue.length() - 1);
          }

          if ("first".equals(relValue)) {
            mFirst = linkPart;
          } else if ("last".equals(relValue)) {
            mLast = linkPart;
          } else if ("next".equals(relValue)) {
            mNext = linkPart;
          } else if ("prev".equals(relValue)) mPrev = linkPart;
        }
      }
    }
  }

  public int getFirstPage() {
    return 1;
  }

  public int getLastPage() {
    if (getPage(mLast) == 0) {
      return getPrevPage() + 1;
    }
    return getPage(mLast);
  }

  public int getPrevPage() {
    if (getPage(mPrev) == 0) {
      return 1;
    }

    return getPage(mPrev);
  }

  public int getNextPage() {
    return getPage(mNext);
  }

  private int getPage(String link) {
    if (TextUtils.isEmpty(link)) {
      return 0;
    } else {
      return Integer.valueOf(link.split("page=")[1]);
    }
  }
}
