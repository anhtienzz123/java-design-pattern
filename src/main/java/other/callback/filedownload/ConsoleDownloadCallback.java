package other.callback.filedownload;

/**
 * A concrete callback: tells the user how the download went.
 */
public class ConsoleDownloadCallback implements DownloadCallback {

    @Override
    public void onSuccess(String fileName, int sizeKb) {
        System.out.println("  [callback] saved " + fileName + " (" + sizeKb + " KB)");
    }

    @Override
    public void onFailure(String url, String reason) {
        System.out.println("  [callback] failed " + url + ": " + reason);
    }
}
