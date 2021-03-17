package com.mscg.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;

public class BinaryDataReaderUtil
 {

  /**
   * Reads a byte array from the provided stream checking if the
   * specified amount of bytes can be read from the source.
   *
   * @param is The input stream from which the byte array will be read.
   * @param bytesToRead The number of bytes that must be read.
   * @return The bytes read from the source.
   * @throws IOException If an error occurs or the EOF is reached before reading
   * all the specified bytes.
   */
  public static byte[] readBytes(InputStream is, int bytesToRead) throws IOException
   {
    byte bytes[] = new byte[bytesToRead];
    int bytesRead = is.read(bytes);
    if(bytesRead != bytesToRead)
     throw new IOException("Unexpected EOF after " + bytesRead + ". " + bytesToRead + " bytes expected");
    return bytes;
   }

  /**
   * Reads a byte array from the provided binary file checking if the
   * specified amount of bytes can be read from the source.
   *
   * @param file The binary file from which the byte array will be read.
   * @param bytesToRead The number of bytes that must be read.
   * @return The bytes read from the source.
   * @throws IOException If an error occurs or the EOF is reached before reading
   * all the specified bytes.
   */
  public static byte[] readBytes(RandomAccessFile file, int bytesToRead) throws IOException
   {
    byte bytes[] = new byte[bytesToRead];
    int bytesRead = file.read(bytes);
    if(bytesRead != bytesToRead)
     throw new IOException("Unexpected EOF after " + bytesRead + ". " + bytesToRead + " bytes expected");
    return bytes;
   }

  /**
   * Reads a short from a little-endian stream.
   *
   * @param is The input stream whose first bytes contain a little-endian short.
   * @return The short value contained in stream.
   * @throws IOException If an error occurs or the EOF is reached before reading
   * all the required bytes.
   */
  public static short readLittleEndianShort(InputStream is) throws IOException
   {
    return (short)readLittleEndianLong(is, 2);
   }

  /**
   * Reads a short from a little-endian binary file.
   *
   * @param file The binary whose first bytes contain a little-endian short.
   * @return The short value contained in stream.
   * @throws IOException If an error occurs or the EOF is reached before reading
   * all the required bytes.
   */
  public static short readLittleEndianShort(RandomAccessFile file) throws IOException
   {
    return (short)readLittleEndianLong(file, 2);
   }

  /**
   * Reads an unsigned short from a little-endian stream.
   *
   * @param is The input stream whose first bytes contain a little-endian unsigned short.
   * @return The unsigned short (as an int) value contained in stream.
   * @throws IOException If an error occurs or the EOF is reached before reading
   * all the required bytes.
   */
  public static int readLittleEndianUnsignedShort(InputStream is) throws IOException
   {
    return (int)readLittleEndianLong(is, 2);
   }

  /**
   * Reads an unsigned short from a little-endian binary file.
   *
   * @param file The binary whose first bytes contain a little-endian unsigned short.
   * @return The unsigned short (as an int) value contained in stream.
   * @throws IOException If an error occurs or the EOF is reached before reading
   * all the required bytes.
   */
  public static int readLittleEndianUnsignedShort(RandomAccessFile file) throws IOException
   {
    return (int)readLittleEndianLong(file, 2);
   }

  /**
   * Reads a short from a big-endian stream.
   *
   * @param is The input stream whose first bytes contain a big-endian short.
   * @return The short value contained in stream.
   * @throws IOException If an error occurs or the EOF is reached before reading
   * all the required bytes.
   */
  public static short readBigEndianShort(InputStream is) throws IOException
   {
    return (short)readBigEndianLong(is, 2);
   }

  /**
   * Reads a short from a big-endian binary file.
   *
   * @param file The binary whose first bytes contain a big-endian short.
   * @return The long value contained in stream.
   * @throws IOException If an error occurs or the EOF is reached before reading
   * all the required bytes.
   */
  public static short readBigEndianShort(RandomAccessFile file) throws IOException
   {
    return (short)readBigEndianLong(file, 2);
   }

  /**
   * Reads an unsigned short from a big-endian stream.
   *
   * @param is The input stream whose first bytes contain a big-endian unsigned short.
   * @return The unsigned short (as an int) value contained in stream.
   * @throws IOException If an error occurs or the EOF is reached before reading
   * all the required bytes.
   */
  public static int readBigEndianUnsignedShort(InputStream is) throws IOException
   {
    return (int)readBigEndianLong(is, 2);
   }

  /**
   * Reads a unsigned short from a big-endian binary file.
   *
   * @param file The binary whose first bytes contain a big-endian unsigned short.
   * @return The unsigned short (as an int) value contained in stream.
   * @throws IOException If an error occurs or the EOF is reached before reading
   * all the required bytes.
   */
  public static int readBigEndianUnsignedShort(RandomAccessFile file) throws IOException
   {
    return (int)readBigEndianLong(file, 2);
   }

  /**
   * Reads an integer from a little-endian stream.
   *
   * @param is The input stream whose first bytes contain a little-endian integer.
   * @return The integer value contained in stream.
   * @throws IOException If an error occurs or the EOF is reached before reading
   * all the required bytes.
   */
  public static int readLittleEndianInt(InputStream is) throws IOException
   {
    return (int)readLittleEndianLong(is, 4);
   }

  /**
   * Reads an integer from a little-endian binary file.
   *
   * @param file The binary whose first bytes contain a little-endian integer.
   * @return The integer value contained in stream.
   * @throws IOException If an error occurs or the EOF is reached before reading
   * all the required bytes.
   */
  public static int readLittleEndianInt(RandomAccessFile file) throws IOException
   {
    return (int)readLittleEndianLong(file, 4);
   }

  /**
   * Reads an integer from a big-endian stream.
   *
   * @param is The input stream whose first bytes contain a big-endian integer.
   * @return The integer value contained in stream.
   * @throws IOException If an error occurs or the EOF is reached before reading
   * all the required bytes.
   */
  public static int readBigEndianInt(InputStream is) throws IOException
   {
    return (int)readBigEndianLong(is, 4);
   }

  /**
   * Reads an integer from a big-endian binary file.
   *
   * @param file The binary whose first bytes contain a big-endian integer.
   * @return The integer value contained in stream.
   * @throws IOException If an error occurs or the EOF is reached before reading
   * all the required bytes.
   */
  public static int readBigEndianInt(RandomAccessFile file) throws IOException
   {
    return (int)readBigEndianLong(file, 4);
   }

  /**
   * Reads an unsigned integer from a little-endian stream.
   *
   * @param is The input stream whose first bytes contain a little-endian integer.
   * @return The unsigned integer (contained in a long) value contained in stream.
   * @throws IOException If an error occurs or the EOF is reached before reading
   * all the required bytes.
   */
  public static long readLittleEndianUnsignedInt(InputStream is) throws IOException
   {
    return readLittleEndianLong(is, 4);
   }

  /**
   * Reads an integer from a little-endian binary file.
   *
   * @param file The binary whose first bytes contain a little-endian integer.
   * @return The unsigned integer (contained in a long) value contained in stream.
   * @throws IOException If an error occurs or the EOF is reached before reading
   * all the required bytes.
   */
  public static long readLittleEndianUnsignedInt(RandomAccessFile file) throws IOException
   {
    return readLittleEndianLong(file, 4);
   }

  /**
   * Reads an unsigned integer from a big-endian stream.
   *
   * @param is The input stream whose first bytes contain a big-endian integer.
   * @return The unsigned integer (contained in a long) value contained in stream.
   * @throws IOException If an error occurs or the EOF is reached before reading
   * all the required bytes.
   */
  public static long readBigEndianUnsignedInt(InputStream is) throws IOException
   {
    return readBigEndianLong(is, 4);
   }

  /**
   * Reads an integer from a big-endian binary file.
   *
   * @param file The binary whose first bytes contain a big-endian integer.
   * @return The unsigned integer (contained in a long) value contained in stream.
   * @throws IOException If an error occurs or the EOF is reached before reading
   * all the required bytes.
   */
  public static long readBigEndianUnsignedInt(RandomAccessFile file) throws IOException
   {
    return readBigEndianLong(file, 4);
   }

  /**
   * Reads a long from a little-endian stream.
   *
   * @param is The input stream whose first bytes contain a little-endian long.
   * @return The long value contained in stream.
   * @throws IOException If an error occurs or the EOF is reached before reading
   * all the required bytes.
   */
  public static long readLittleEndianLong(InputStream is) throws IOException
   {
    return readLittleEndianLong(is, 8);
   }

  /**
   * Reads a long from a little-endian binary file.
   *
   * @param file The binary whose first bytes contain a little-endian long.
   * @return The long value contained in stream.
   * @throws IOException If an error occurs or the EOF is reached before reading
   * all the required bytes.
   */
  public static long readLittleEndianLong(RandomAccessFile file) throws IOException
   {
    return readLittleEndianLong(file, 8);
   }

  /**
   * Reads a long from a big-endian stream.
   *
   * @param is The input stream whose first bytes contain a big-endian long.
   * @return The long value contained in stream.
   * @throws IOException If an error occurs or the EOF is reached before reading
   * all the required bytes.
   */
  public static long readBigEndianLong(InputStream is) throws IOException
   {
    return readBigEndianLong(is, 8);
   }

  /**
   * Reads a long from a big-endian binary file.
   *
   * @param file The binary whose first bytes contain a big-endian long.
   * @return The long value contained in stream.
   * @throws IOException If an error occurs or the EOF is reached before reading
   * all the required bytes.
   */
  public static long readBigEndianLong(RandomAccessFile file) throws IOException
   {
    return readBigEndianLong(file, 8);
   }

  /**
   * Reads a long from a little-endian stream.
   *
   * @param is The input stream whose first bytes contain a little-endian long.
   * @param bytesToRead The number of bytes that must be read from the stream.
   * @return The long value contained in stream.
   * @throws IOException If an error occurs or the EOF is reached before reading
   * all the required bytes.
   */
  protected static long readLittleEndianLong(InputStream is, int bytesToRead) throws IOException
   {
    return readLittleEndianLong(readBytes(is, bytesToRead));
   }

  /**
   * Reads a long from a little-endian binary file.
   *
   * @param file The binary whose first bytes contain a little-endian long.
   * @param bytesToRead The number of bytes that must be read from the stream.
   * @return The long value contained in stream.
   * @throws IOException If an error occurs or the EOF is reached before reading
   * all the required bytes.
   */
  protected static long readLittleEndianLong(RandomAccessFile file, int bytesToRead) throws IOException
   {
    return readLittleEndianLong(readBytes(file, bytesToRead));
   }

  /**
   * Reads a long from a big-endian stream.
   *
   * @param is The input stream whose first bytes contain a big-endian long.
   * @param bytesToRead The number of bytes that must be read from the stream.
   * @return The long value contained in stream.
   * @throws IOException If an error occurs or the EOF is reached before reading
   * all the required bytes.
   */
  protected static long readBigEndianLong(InputStream is, int bytesToRead) throws IOException
   {
    return readBigEndianLong(readBytes(is, bytesToRead));
   }

  /**
   * Reads a long from a big-endian binary file.
   *
   * @param file The binary whose first bytes contain a big-endian long.
   * @param bytesToRead The number of bytes that must be read from the stream.
   * @return The long value contained in stream.
   * @throws IOException If an error occurs or the EOF is reached before reading
   * all the required bytes.
   */
  public static long readBigEndianLong(RandomAccessFile file, int bytesToRead) throws IOException
   {
    return readBigEndianLong(readBytes(file, bytesToRead));
   }

  /**
   * Reads a long from a little-endian byte array.
   *
   * @param data The little-endian byte array.
   * @return The long value contained in the array.
   */
  public static long readLittleEndianLong(byte[] data)
   {
    long ret = 0;
    for (int i = 0, l = data.length; i < l; i++)
     {
      ret += ((long) ((int) data[i] & 0xFF)) << (8 * i);
     }
    return ret;
   }

  /**
   * Reads a long from a big-endian byte array.
   *
   * @param data The big-endian byte array.
   * @return The long value contained in the array.
   */
  public static long readBigEndianLong(byte[] data)
   {
    long ret = 0;
    for (int i = 0, l = data.length; i < l; i++)
     {
      ret += ((long) ((int) data[i] & 0xFF)) << (8 * (l - i - 1));
     }
    return ret;
   }

 }
