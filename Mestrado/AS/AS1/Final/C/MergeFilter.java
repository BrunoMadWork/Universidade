/******************************************************************************************************************
* File:MiddleFilter.java
* Course: 17655
* Project: Assignment 1
* Copyright: Copyright (c) 2003 Carnegie Mellon University
* Versions:
*	1.0 November 2008 - Sample Pipe and Filter code (ajl).
*
* Description:
*
* This class serves as an example for how to use the FilterRemplate to create a standard filter. This particular
* example is a simple "pass-through" filter that reads data from the filter's input port and writes data out the
* filter's output port.
*
* Parameters: 		None
*
* Internal Methods: None
*
******************************************************************************************************************/
import java.util.*;                     // This class is used to interpret time words
import java.text.SimpleDateFormat;      // This class is used to format and write time in a string format.
import java.nio.*;

public class MergeFilter extends FilterFramework
{	ArrayList<Long> array;
    ArrayList<Integer> arrayIds;
	public void run()
    {

    	 int MeasurementLength = 8;      // This is the length of all measurements (including time) in bytes
        int IdLength = 4;

		int bytesread = 0;					// Number of bytes read from the input file.
		int byteswritten = 0;				// Number of bytes written to the stream.
		byte databyte = 0;					// The byte of data read from the file
		long measurement;               // This is the word used to store all measurements - conversions are illustrated.
        int id;                         // This is the measurement id
        int i;                          // This is a loop counter
        array = new ArrayList<Long>();
        arrayIds= new ArrayList<Integer>();
       
		// Next we write a message to the terminal to let the world know we are alive...

		System.out.print( "\n" + this.getName() + "::Merge Reading ");

		while (true)
		{
			/*************************************************************
			*	Here we read a byte and write a byte
			*************************************************************/

			try
			{
			
				id = 0;
 
                for (i=0; i<IdLength; i++ )
                {
                    databyte = ReadFilterInputPort();   // This is where we read the byte from the stream...
 
                    id = id | (databyte & 0xFF);        // We append the byte on to ID...
 
                    if (i != IdLength-1)                // If this is not the last byte, then slide the
                    {                                   // previously appended byte to the left by one byte
                        id = id << 8;                 // to make room for the next byte we append to the ID
 
                    } // if
 
                    bytesread++;                        // Increment the byte count
 
                } // for
 
                /****************************************************************************
                // Here we read measurements. All measurement data is read as a stream of bytes
                // and stored as a long value. This permits us to do bitwise manipulation that
                // is neccesary to convert the byte stream into data words. Note that bitwise
                // manipulation is not permitted on any kind of floating point types in Java.
                // If the id = 0 then this is a time value and is therefore a long value - no
                // problem. However, if the id is something other than 0, then the bits in the
                // long value is really of type double and we need to convert the value using
                // Double.longBitsToDouble(long val) to do the conversion which is illustrated.
                // below.
                *****************************************************************************/
 
                measurement = 0;
 
                for (i=0; i<MeasurementLength; i++ )
                {
                    databyte = ReadFilterInputPort();
                    measurement = measurement | (databyte & 0xFF);  // We append the byte on to measurement...
 
                    if (i != MeasurementLength-1)                   // If this is not the last byte, then slide the
                    {                                               // previously appended byte to the left by one byte
                        measurement = measurement << 8;               // to make room for the next byte we append to the
                                                                    // measurement
                    } // if
 
                    bytesread++;                                    // Increment the byte count
 
                } // if
                arrayIds.add(id);
                array.add(measurement);
 	      id = 0;
 
                for (i=0; i<IdLength; i++ )
                {
                    databyte = ReadFilterInputPort2();   // This is where we read the byte from the stream...
 
                    id = id | (databyte & 0xFF);        // We append the byte on to ID...
 
                    if (i != IdLength-1)                // If this is not the last byte, then slide the
                    {                                   // previously appended byte to the left by one byte
                        id = id << 8;                 // to make room for the next byte we append to the ID
 
                    } // if
 
                    bytesread++;                        // Increment the byte count
 
                } // for
                /****************************************************************************
                // Here we read measurements. All measurement data is read as a stream of bytes
                // and stored as a long value. This permits us to do bitwise manipulation that
                // is neccesary to convert the byte stream into data words. Note that bitwise
                // manipulation is not permitted on any kind of floating point types in Java.
                // If the id = 0 then this is a time value and is therefore a long value - no
                // problem. However, if the id is something other than 0, then the bits in the
                // long value is really of type double and we need to convert the value using
                // Double.longBitsToDouble(long val) to do the conversion which is illustrated.
                // below.
                *****************************************************************************/
 
                measurement = 0;
 
                for (i=0; i<MeasurementLength; i++ )
                {
                    databyte = ReadFilterInputPort2();
                    measurement = measurement | (databyte & 0xFF);  // We append the byte on to measurement...
 
                    if (i != MeasurementLength-1)                   // If this is not the last byte, then slide the
                    {                                               // previously appended byte to the left by one byte
                        measurement = measurement << 8;               // to make room for the next byte we append to the
                                                                    // measurement
                    } // if
 
                    bytesread++;                                    // Increment the byte count
 
                } // if
              //  System.out.print(" ID = " + id + "  " + Double.longBitsToDouble(measurement) +"\n");
                arrayIds.add(id);
                array.add(measurement);
 
 

			} // try

			catch (EndOfStreamException e)
			{
				
                                    for(int j=0;j<array.size();j++){
                        byte[] temp=LongToByteArray(array.get(j));
                        byte[] tempIds=IntToByteArray(arrayIds.get(j));
                        for(int it=0; it<tempIds.length;it++){
                            WriteFilterOutputPort(tempIds[it]);
                             byteswritten++;
 
                        }
                        for(int it=0; it<temp.length;it++){
                            WriteFilterOutputPort(temp[it]);
                             byteswritten++;
 
                        }
                     }
 
            System.out.print( "\n" + this.getName() + "MERGE EXITING; bytes read: " + bytesread + " bytes written: " + byteswritten );





				break;

			} // catch

		



		} // while



   } // run

    byte[] IntToByteArray(int value){
          return new byte[] {
            (byte)(value >>> 24),
            (byte)(value >>> 16),
            (byte)(value >>> 8),
            (byte)value};
 
   }
 
   byte[] LongToByteArray(long input){
 
    byte[] bytes = ByteBuffer.allocate(8).putLong(input).array();
    return bytes;
 
 
   }

} // MiddleFilter