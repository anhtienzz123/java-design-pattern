package other.callback.fileupload;

/**
 * Callback Pattern demo (synchronous, with lambdas).
 *
 * The same uploader reports progress; each client decides how to show it
 * by passing a different callback.
 */
public class ZMain {

    public static void main(String[] args) {
        FileUploader uploader = new FileUploader();

        System.out.println("--- Callback as a lambda: text log ---");
        uploader.upload("photo.jpg",
                (file, percent) -> System.out.println("  " + file + ": " + percent + "%"));

        System.out.println("\n--- Callback as a lambda: progress bar ---");
        uploader.upload("video.mp4", (file, percent) -> {
            String bar = "#".repeat(percent / 10) + "-".repeat(10 - percent / 10);
            System.out.println("  [" + bar + "] " + percent + "%");
        });

        System.out.println("\n--- Callback as a method reference ---");
        uploader.upload("notes.txt", ZMain::notifyWhenDone);
    }

    private static void notifyWhenDone(String file, int percent) {
        if (percent == 100) {
            System.out.println("  Notification: " + file + " uploaded!");
        }
    }
}
