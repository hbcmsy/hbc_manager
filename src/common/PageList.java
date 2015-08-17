package common;

import java.util.List;

public class PageList<T> {

    private String pageBar;
    private String numPageBar;
    private List<T> list;

    public PageList(List<T> list, int count, int pageSize, int pageNo, String url) {
        if(count==0){
            pageBar="";
            numPageBar="";
            return;
        }
        //计算总页数
        int pageCount = count / pageSize + (count % pageSize == 0 ? 0 : 1);
        //判断当前页号的合法性
        if (pageNo < 1) {
            pageNo = 1;
        }
        if (pageNo > pageCount) {
            pageNo = pageCount;
        }

        StringBuffer sb = new StringBuffer();
        sb.append("每页:").append(pageSize).append("&nbsp;页次:").append(pageNo).append("/").append(pageCount).append("&nbsp;总计:").append(count).append("&nbsp;");
        if (pageNo == 1) {
            sb.append("首页&nbsp;上页&nbsp;");
        } else {
            sb.append("<a href=\"").append(url).append("?pageNo=").append("1\">首页</a>&nbsp;");
            sb.append("<a href=\"").append(url).append("?pageNo=").append(pageNo - 1).append("\">上页</a>&nbsp;");
        }
        if (pageNo == pageCount) {
            sb.append("下页&nbsp;尾页");
        } else {
            sb.append("<a href=\"").append(url).append("?pageNo=").append(pageNo + 1).append("\">下页</a>&nbsp;");
            sb.append("<a href=\"").append(url).append("?pageNo=").append(pageCount).append("\">尾页</a>");
        }
        pageBar = sb.toString();

        sb = new StringBuffer();

        //计算当前页所在的组
        int group = pageNo / 10 + (pageNo % 10 == 0 ? 0 : 1);
        int start = (group - 1) * 10 + 1;
        int end = start + 9;
        if (end > pageCount) {
            end = pageCount;
        }

        if (start > 1) {
            sb.append("&nbsp;<a href=\"JavaScript:").append(url).append("?pageNo=").append(start - 1).append("\">&lt;</a>");
        }
        for (int i = start; i <= end; i++) {
            if (pageNo != i) {
                sb.append("[<a href=\"").append(url).append("?pageNo=").append(i).append("\">").append(i).append("</a>]");
            } else {
                sb.append("[").append(i).append("]");
            }
        }
        if (end < pageCount) {
            sb.append("<a href=\"").append(url).append("?pageNo=").append(end + 1).append("\">&gt;</a>&nbsp;");
        }

        numPageBar = sb.toString();
        this.list = list;
    }

    public String getPageBar() {
        return pageBar;
    }

    public void setPageBar(String pageBar) {
        this.pageBar = pageBar;
    }

    public String getNumPageBar() {
        return numPageBar;
    }

    public void setNumPageBar(String numPageBar) {
        this.numPageBar = numPageBar;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
    
}
