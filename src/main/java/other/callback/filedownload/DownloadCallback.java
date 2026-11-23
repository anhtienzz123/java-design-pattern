package other.callback.filedownload;

/**
 * The callback: code the caller hands over, to be run by the downloader
 * when the download finishes.
 */
public interface DownloadCallback {

    void onSuccess(String fileName, int sizeKb);

    void onFailure(String url, String reason);
}
