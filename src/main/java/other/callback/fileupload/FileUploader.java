package other.callback.fileupload;

/**
 * Calls the callback synchronously, after every uploaded chunk.
 * It reports progress but does not care how it is shown.
 */
public class FileUploader {

    private static final int CHUNKS = 4;

    public void upload(String fileName, ProgressCallback callback) {
        for (int chunk = 1; chunk <= CHUNKS; chunk++) {
            // ... send one chunk to the server ...
            callback.onProgress(fileName, chunk * 100 / CHUNKS);
        }
    }
}
