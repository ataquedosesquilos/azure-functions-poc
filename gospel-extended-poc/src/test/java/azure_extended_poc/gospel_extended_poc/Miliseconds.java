package azure_extended_poc.gospel_extended_poc;

public class Miliseconds {
	
	public static void main(String[] args) {
        Long milliseconds = 1000000L;
        Long minutes = (milliseconds / 1000) / 60;
        Long seconds = (milliseconds / 1000) % 60;
        Long milliseconds2 = milliseconds % 1000;
        System.out.format("%d Milliseconds = %d minutes and %d seconds and %d miliseconds", milliseconds.intValue(), minutes.intValue(), seconds.intValue(), milliseconds2.intValue());
    }
}
