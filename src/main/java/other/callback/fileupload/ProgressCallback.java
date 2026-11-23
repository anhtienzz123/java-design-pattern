package other.callback.fileupload;

/**
 * The callback. It has a single method, so the client can pass a lambda
 * or a method reference.
 */
@FunctionalInterface
public interface ProgressCallback {

    void onProgress(String fileName, int percent);
}
