package other.callback.filedownload;

/**
 * Callback Pattern demo.
 *
 * The client passes a callback to the downloader and moves on. When the
 * download finishes, the downloader calls the callback with the result.
 */
public class ZMain {

    public static void main(String[] args) throws InterruptedException {
        FileDownloader downloader = new FileDownloader();

        System.out.println("--- Named callback class ---");
        Thread first = downloader.download("https://example.com/report.pdf", new ConsoleDownloadCallback());
        System.out.println("  [main] not waiting, doing other work...");
        first.join();

        System.out.println("\n--- Same downloader, different callback ---");
        Thread second = downloader.download("https://example.com/setup.exe", new DownloadCallback() {
            @Override
            public void onSuccess(String fileName, int sizeKb) {
                System.out.println("  [callback] open " + fileName + " in viewer");
            }

            @Override
            public void onFailure(String url, String reason) {
                System.out.println("  [callback] show error dialog: " + reason);
            }
        });
        System.out.println("  [main] not waiting, doing other work...");
        second.join();
    }
}
