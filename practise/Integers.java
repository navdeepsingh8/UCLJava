public class Integers
{
	public static void main (final String[] args) {
		Integers x = new Integers() ;
		x.readNumbers();
		x.writeNumbers();
	}

	KeyboardInput in = new KeyboardInput() ;
	int[] array = new int[10];

	public void readNumbers() {
		for (int i=0; i<10; i++) {
			System.out.print("Enter number " + i + ":") ;
			array[i] = in.readInteger() ;
		}
	}

	public void writeNumbers() {
		for (int i=0; i<10; i++) {
			System.out.print("Integer" + i + ":" + array[i]) ;
			}
	}
}