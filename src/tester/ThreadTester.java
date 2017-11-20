package tester;

public class ThreadTester implements Runnable{
	int x=0;
	int y=0;
	public int getX(){
		return x;
	}
	public int getY(){
		return y;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		x++;
		y++;
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
