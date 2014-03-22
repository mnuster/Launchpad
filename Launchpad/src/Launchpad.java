import java.util.Random;

import javax.sound.midi.Instrument;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiDevice.Info;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Receiver;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Soundbank;
import javax.sound.midi.Synthesizer;
import javax.sound.midi.Transmitter;

import lib.LPLib;

public class Launchpad {
	
	private int[][] grid = new int[LPLib.X_DIM][LPLib.Y_DIM];
	private boolean clear = true;
	
	private Receiver receiver;
	private MidiDevice receiverDevice;
	
	private Transmitter transmitter;
	private MidiDevice transmitterDevice;
	
	private Synthesizer synth;
	
	private ShortMessage msg = new ShortMessage();
	
	private int buffer = 0;

	public Launchpad() {
		grid = new int[LPLib.X_DIM][LPLib.Y_DIM];
	}
	
	public Launchpad init() {
		if (findReciever() && findTransmitter()) {
			System.out.println("Devices found!");
			LPLib.initLetters(false);
			initBuffer();
			
			try {
				synth = MidiSystem.getSynthesizer();
				transmitter.setReceiver(synth.getReceiver());
				
				
				Soundbank sb = synth.getDefaultSoundbank();
		        if (sb != null) {
		            Instrument[] instruments = synth.getDefaultSoundbank().getInstruments();
		            synth.loadInstrument(instruments[0]);
		        }
				
				
				
				
				
				
				printSynthInfo(synth);
				System.out.println("synth set");
			} catch (MidiUnavailableException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			
			
			return this;
		} else {
			System.err.println("Devices NOT found");
			return null;
		}
	}	
	
	public void markLEDSet(int x, int y, int color, boolean redraw) {
		int button = x + (y * LPLib.Y_DIM * 2);
		if (button < 0 && button > 127) return;
		
		grid[x][y] = color;
		clear = false;
		
		if (redraw) redraw();
	}
	
	public void markLEDClear(int x, int y, boolean redraw) {
		int button = x + (y * LPLib.Y_DIM * 2);
		if (button < 0 && button > 127) return;
		
		grid[x][y] = LPLib.CLEAR;

		if (redraw) redraw();
	}
	
	public void markGridClear(boolean redraw) {
		grid = new int[LPLib.X_DIM][LPLib.Y_DIM];
		clear = true;
		
		if (redraw) redraw();
	}
	
	public void redraw() {			
		if (clear) {
			//System.out.println("Clearing!");
			try {
				msg.setMessage(ShortMessage.CONTROL_CHANGE, 0, 0, 0);
				receiver.send(msg, -1);
			} catch (InvalidMidiDataException e) {
				e.printStackTrace();
			}
			initBuffer();
		} else {
			//System.out.println("Drawing buffer " + buffer);
			for (int j = 0; j < LPLib.Y_DIM; j++) {
				for (int i = 0; i < LPLib.X_DIM; i = i + 2) {
					try {
						msg.setMessage(146, 2, grid[i][j], grid[i+1][j]);
						receiver.send(msg, -1);
					} catch (InvalidMidiDataException e) {
						e.printStackTrace();
					}
					
//					try {
//						Thread.sleep(100);
//					} catch (InterruptedException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
				}
			}
		}		
		swapBuffer();
	}
	
	public void redrawRand(int cycles) {
		int[] colors = LPLib.COLORS_ARRAY;
		int len = colors.length;
		Random gen = new Random();
		for (int c = 0; c < cycles; c++) {
			int color = colors[gen.nextInt(len)];
			for (int i = 0; i < 64; i++) {
				try {
					msg.setMessage(146, 2, color, color);
					receiver.send(msg, -1);
				} catch (InvalidMidiDataException e) {
					e.printStackTrace();
				}
			}
			swapBuffer();
		}
	}
	
	public void setDuty(int numerator, int denominator) {
		if (numerator < 1 || numerator > 16 || denominator < 3 || denominator > 18) {
			System.err.println("invalid duty cycle");
		} else {
			int type = 30;
			int data = 2;
			if (numerator < 9) {
				data = (16 * (numerator - 1)) + (denominator - 3);
			} else {
				type = 31;
				data = (16 * (numerator - 9)) + (denominator - 3);
			}
			try {
				msg.setMessage(ShortMessage.CONTROL_CHANGE, 0, type, data);
				receiver.send(msg, -1);
			} catch (InvalidMidiDataException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void initBuffer() {
		buffer = LPLib.DEFAULT_BUFFER;
		swapBuffer();
	}
	
	private void swapBuffer() {
		try {
			msg.setMessage(ShortMessage.CONTROL_CHANGE, 0, 0, buffer);
			receiver.send(msg, -1);
		} catch (InvalidMidiDataException e) {
			e.printStackTrace();
		}
		buffer = Math.abs(buffer - LPLib.BUFFER_SWAP);
	}
	
	public void rotateUp(int cycles) {
		int[] temp = new int[8];
		for (int cycle = 0; cycle < cycles; cycle++) {
			for (int c = 0; c < LPLib.X_DIM; c++) {
				
				temp = grid[0];
				for (int i = 0; i < grid.length - 1; i++) {
					grid[i] = grid[i+1];
				}
				grid[grid.length-1] = temp;
//				try {
//					Thread.sleep(200);
//				} catch (InterruptedException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
				redraw();
			}
		}
	}
	
	public void rotateLeft(int cycles) {
		int temp;
		for (int cycle = 0; cycle < cycles; cycle++) {
			for (int c = 0; c < LPLib.X_DIM; c++) {
				for (int i = 0; i < grid.length; i++) {
					temp = grid[i][0];
					for (int j = 0; j < grid[i].length-1; j++) {
						grid[i][j] = grid[i][j+1];
					}
					grid[i][grid[i].length-1] = temp;
				}
//				try {
//					Thread.sleep(50);
//				} catch (InterruptedException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
				redraw();
			}
		}
	}
	
	public void rotateLeft(int cycles, int[][] lineGrid ) {
		int[][] tempGrid = grid;
		for (int cycle = 0; cycle < cycles; cycle++) {
			for (int cursor = 0; cursor < lineGrid.length; cursor++) {
				for (int i = 0; i < LPLib.X_DIM; i++) {
					tempGrid[i] = lineGrid[(cursor+i)%lineGrid.length];
					clear = false;
				}
//				try {
//					Thread.sleep(25);
//				} catch (InterruptedException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
				redraw();
			}
		}
			
	}
	
	public void printGrid() {
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[i].length; j++) {
				System.out.print(grid[i][j]+"\t");
			}
			System.out.println();
		}		
	}
	
	private boolean findReciever() {
		MidiDevice.Info[] infos = MidiSystem.getMidiDeviceInfo();
		for (Info info : infos) {
			System.out.println(info.getName());
			if (info.getName().equalsIgnoreCase("launchpad")) {
				try {
					MidiDevice device = MidiSystem.getMidiDevice(info);
					device.open();
					if (device.getMaxReceivers() != 0) {
						// receiver found;
						receiverDevice = device;
						receiver = device.getReceiver();
						return true;
					}
					device.close();
				} catch (MidiUnavailableException e) {
					e.printStackTrace();
				}
			}
		}
		return false;
	}

	private boolean findTransmitter() {
		MidiDevice.Info[] infos = MidiSystem.getMidiDeviceInfo();
		for (Info info : infos) {
			if (info.getName().equalsIgnoreCase("launchpad")) {
				try {
					MidiDevice device = MidiSystem.getMidiDevice(info);
					device.open();
					if (device.getMaxTransmitters() != 0) {
						// transmitter found;
						transmitterDevice = device;
						transmitter = device.getTransmitter();
						return true;
					}
					device.close();
				} catch (MidiUnavailableException e) {
					e.printStackTrace();
				}
			}
		}
		return false;
	}

	public void close(boolean clear) {
		if (clear) markGridClear(true);

		receiver.close();
		receiverDevice.close();
		transmitter.close();
		transmitterDevice.close();
		System.out.println("Devices Closed!");
	}
	
	private void printSynthInfo(Synthesizer synth) {
		System.out.println(synth.toString());
		System.out.println(synth.getMaxReceivers());
		
		for (MidiChannel chan : synth.getChannels()) {
			System.out.println(chan.toString());
		}
		System.out.println(synth.getAvailableInstruments());
		
	}

}
