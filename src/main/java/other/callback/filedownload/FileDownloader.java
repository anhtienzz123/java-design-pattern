package other.callback.filedownload;

/**
 * The caller of the callback. It does the work in the background and then
 * "calls back" — it never knows what the client wants to do with the result.
 */
public class FileDownloader {

    public Thread download(String url, DownloadCallback callback) {
        return Thread.startVirtualThread(() -> {
            System.out.println("  [downloader] downloading " + url + " ...");
            sleep(300);

            if (url.endsWith(".exe")) {
                callback.onFailure(url, "blocked file type");
            } else {
                String fileName = url.substring(url.lastIndexOf('/') + 1);
                callback.onSuccess(fileName, 512);
            }
        });
    }

    private static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
