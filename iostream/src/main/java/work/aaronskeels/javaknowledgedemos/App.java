package work.aaronskeels.javaknowledgedemos;

public class App 
{
    public static void main( String[] args )
    {
        /*
         * IO Streams can be classified as either a byte or character stream.
         * Byte Streams Derived From:
         * - InputStream
         *      - FileInputStream
         *      - ByteArrayInputStream
         *      - ObjectInputStream
         *      - BufferedInputStream (Best Performance)
         * - OutputStream
         *      - FileOutputStream
         *      - ByteArrayOutputStream
         *      - ObjectOutputStream
         *      - BufferedOutputStream (Best Performance)
         *      - PrintStream (This seems like it should be a Character stream but it extends OutputStream so?)
         * Character Streams Derived From:
         * - Reader
         *      - InputStreamReader
         *      - FileReader
         *      - StringReader
         *      - BufferedReader (Best Performance)
         *      - PrintStream (This doesn't belong here but we are including it because I am sensible unlike Java devs)
         * - Writer
         *      - OutputStreamWriter
         *      - FileWriter
         *      - StringWriter
         *      - BufferedWriter (Best Performance)
         *      - PrintWriter (Why is this a Character stream but PrintStream is a Byte stream? Weirdos.)
         * Bonus Types:
         * - Scanner (This is a higher-level stream tokenizer which can handle different data types cleanly)
         * - DeflaterOutputStream & InflaterInputStream (Compression writer & decompression reader)
         * - PipedOutputStream & PipedInputStream (Used for inter-thread communciation)
         * - SequenceInputStream (Concatenates multiple input streams into a single cohesive input stream) (No output counterpart)
         * - RandomAccessFile (Allows reading & writing @ specific position in file)
         * Notes:
         * - Remember to flush streams where applicable.
         * - Remember to close streams unless you try-with-resource
         */
    }
}
