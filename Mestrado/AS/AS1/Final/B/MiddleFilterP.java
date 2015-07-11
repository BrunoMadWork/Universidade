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
import java.util.*;						// This class is used to interpret time words
import java.text.SimpleDateFormat;		// This class is used to format and write time in a string format.
import java.nio.*;

public class MiddleFilterP extends FilterFramework
{
	ArrayList<Long> array;
	ArrayList<Long> rejeitados;
	ArrayList<Integer> arrayIds;
	ArrayList<Integer> rejeitadosIds;
	public void run()
    {
		/************************************************************************************
		*	TimeStamp is used to compute time using java.util's Calendar class.
		* 	TimeStampFormat is used to format the time value so that it can be easily printed
		*	to the terminal.
		*************************************************************************************/

		Calendar TimeStamp = Calendar.getInstance();
		SimpleDateFormat TimeStampFormat = new SimpleDateFormat("yyyy MM dd::hh:mm:ss:SSS");

		int MeasurementLength = 8;		// This is the length of all measurements (including time) in bytes
		int IdLength = 4;				// This is the length of IDs in the byte stream

		byte databyte = 0;				// This is the data byte read from the stream
		int bytesread = 0;				// This is the number of bytes read from the stream
		int byteswritten = 0;				// Number of bytes written to the stream.


		long measurement;				// This is the word used to store all measurements - conversions are illustrated.
		int id;							// This is the measurement id
		int i;							// This is a loop counter
		array = new ArrayList<Long>();
		arrayIds= new ArrayList<Integer>();
		rejeitados = new ArrayList<Long>();
		rejeitadosIds= new ArrayList<Integer>();

		/*************************************************************
		*	First we announce to the world that we are alive...
		**************************************************************/

		System.out.print( "\n" + this.getName() + "::T filter Reading ");

		while (true)
		{
			try
			{
				/***************************************************************************
				// We know that the first data coming to this filter is going to be an ID and
				// that it is IdLength long. So we first decommutate the ID bytes.
				****************************************************************************/

				id = 0;

				for (i=0; i<IdLength; i++ )
				{
					databyte = ReadFilterInputPort();	// This is where we read the byte from the stream...

					id = id | (databyte & 0xFF);		// We append the byte on to ID...

					if (i != IdLength-1)				// If this is not the last byte, then slide the
					{									// previously appended byte to the left by one byte
						id = id << 8;					// to make room for the next byte we append to the ID

					} // if

					bytesread++;						// Increment the byte count

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
					measurement = measurement | (databyte & 0xFF);	// We append the byte on to measurement...

					if (i != MeasurementLength-1)					// If this is not the last byte, then slide the
					{												// previously appended byte to the left by one byte
						measurement = measurement << 8;				// to make room for the next byte we append to the
																	// measurement
					} // if

					bytesread++;									// Increment the byte count

				} // if
				arrayIds.add(id);
				array.add(measurement);


				/****************************************************************************
				// Here we look for an ID of 0 which indicates this is a time measurement.
				// Every frame begins with an ID of 0, followed by a time stamp which correlates
				// to the time that each proceeding measurement was recorded. Time is stored
				// in milliseconds since Epoch. This allows us to use Java's calendar class to
				// retrieve time and also use text format classes to format the output into
				// a form humans can read. So this provides great flexibility in terms of
				// dealing with time arithmetically or for string display purposes. This is
				// illustrated below.
				****************************************************************************/

				if ( id == 0 )
				{
					TimeStamp.setTimeInMillis(measurement);

				} // if
				
					
				
				/****************************************************************************
				// Here we pick up a measurement (ID = 4 in this case), but you can pick up
				// any measurement you want to. All measurements in the stream are
				// decommutated by this class. Note that all data measurements are double types
				// This illustrates how to convert the bits read from the stream into a double
				// type. Its pretty simple using Double.longBitsToDouble(long value). So here
				// we print the time stamp and the data associated with the ID we are interested
				// in.
				****************************************************************************/

				/*if ( id == 3 )
				{
					Double measurementaux=(Double.longBitsToDouble(measurement)) ; // TEM DE SE PASSAR PARA FLOAT ACHO EU
					if(measurementaux<=80&&measurementaux>=50){
						if(lastValid==-1){
							lastValid=measurementaux;
						}

					}
					else{

					}
					
					measurement= Double.doubleToRawLongBits(measurementaux);

			

				} // if*/


				//byte[] bufferId=IntToByteArray(id);
				//byte[] bufferMeasurement=LongToByteArray(measurement);

			//	System.out.println("O tamanho do id em bytes é: "+ bufferId.length);

				
				
				//System.out.println("O tamanho dos dados em bytes é: "+ bufferMeasurement.length);

				


			} // try

			/*******************************************************************************
			*	The EndOfStreamExeception below is thrown when you reach end of the input
			*	stream (duh). At this point, the filter ports are closed and a message is
			*	written letting the user know what is going on.
			********************************************************************************/

			catch (EndOfStreamException e)
			{
				TrataPsi();

				
				for(int j=0;j<rejeitados.size();j++){
					 	byte[] temp=LongToByteArray(rejeitados.get(j));
					 	byte[] tempIds=IntToByteArray(rejeitadosIds.get(j));
					 	for(int it=0; it<tempIds.length;it++){
   					 		WriteFilterOutputPort(tempIds[it]);
   							 byteswritten++;

						}
						for(int it=0; it<temp.length;it++){
   					 		WriteFilterOutputPort(temp[it]);
   							 byteswritten++;

						}
					 }
				
				
				

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
				ClosePorts();
				System.out.print( "\n" + this.getName() + "::P filter Exiting; bytes read: " + bytesread +" bytes written :"+byteswritten);
				break;

			} // catch

		} // while
					 /*(int j=0;j<array.size();j++){
					 	byte[] temp=LongToByteArray(array.get(j));
						for(int it=0; it<temp.length;it++){
   					 		WriteFilterOutputPort(temp[it]);
   							 byteswritten++;

						}
					 }*/

				


   } // run
   Double ReturnRightValue( Double temperatureFa) {
   	 return ((temperatureFa - 32)*5)/9;

   }
 
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
   void TrataPsi(){
	double lastValid=-1;
	long temporario=0;
   	System.out.println("Tamanho:" + array.size());
   	for(int i=0;i<array.size();i++){
   		int idactual=arrayIds.get(i);
   		double valoractual=Double.longBitsToDouble(array.get(i));
		
		if(idactual==0)
		{
			temporario = array.get(i);
		}
		
   		if(idactual==3){
   			   		System.out.println("Valor actual---:"+valoractual+"id ----"+idactual);
					
					if((valoractual<50 || valoractual>80) && lastValid == -1)
					{
						rejeitados.add(temporario);
						rejeitadosIds.add(0);
						System.out.println(temporario);
						rejeitados.add(Double.doubleToRawLongBits(valoractual));
						rejeitadosIds.add(7);
						for(int j=0;j<array.size();j++)
						{
							int idactual2=arrayIds.get(j);
							if(idactual2==3)
							{
								double valoractual2=Double.longBitsToDouble(array.get(j));
								if(valoractual2>= 50 && valoractual2<=80)
								{
									lastValid=valoractual2;
									break;
								}
							}
						}
						System.out.println("Novo valor actual---:"+lastValid+"id ----"+idactual);
						array.set(i,Double.doubleToRawLongBits(lastValid));
					}
					else if(valoractual<50 || valoractual>80)
					{
						rejeitados.add(temporario);
						rejeitadosIds.add(0);
						System.out.println(temporario);
						rejeitados.add(Double.doubleToRawLongBits(valoractual));
						rejeitadosIds.add(7);
						int pontofinal = 0;
						for(int j=i+1;j<array.size();j++)
						{
							int idactual2=arrayIds.get(j);
							double valoractual2=Double.longBitsToDouble(array.get(j));
							if(idactual2==3 && valoractual2>=50 && valoractual2<=80)
							{
								 double novovalor = 0;
								 novovalor = (lastValid + valoractual2) / 2;
								 lastValid = novovalor;
								 System.out.println("Novo valor actual---:"+novovalor+"id ----"+idactual);
								 array.set(i,Double.doubleToRawLongBits(novovalor));
								 pontofinal = 1;
								 break;
							}
						}
						if(pontofinal==0)
						{
							array.set(i,Double.doubleToRawLongBits(lastValid));
							System.out.println("Novo valor actual---:"+lastValid+"id ----"+idactual);
						}
					}
					else if(valoractual >= 50 && valoractual <=80)
					{
						lastValid = valoractual;
						System.out.println("Novo valor actual---:"+lastValid+"id ----"+idactual);
					}

   		}

   	}

   }
  


} 