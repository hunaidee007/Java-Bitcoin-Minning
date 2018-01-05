import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Test {

	static List<Block> blockChain = new ArrayList<Block>();
	
	public static int difficulty = 3;

	public static void main(String[] args) throws InterruptedException {

	  BitCoinEngine bitCoinEngine = new BitCoinEngine();
	  
	  ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
	  scheduledExecutorService.scheduleWithFixedDelay(new Runnable() {
		
		@Override
		public void run() {
			bitCoinEngine.releaseBlock();
			
		}
	   }, 5,5, TimeUnit.SECONDS);
	  
	  Account a = new Account(bitCoinEngine,"A");
	  Account b = new Account(bitCoinEngine,"B");
	  Account c = new Account(bitCoinEngine,"C");
	  Account d = new Account(bitCoinEngine,"D");
	  Account e = new Account(bitCoinEngine,"E");
	  Account f = new Account(bitCoinEngine,"F");
	  
	  
	  ScheduledExecutorService mineBlockExecutor = Executors.newScheduledThreadPool(1);
	  mineBlockExecutor.scheduleWithFixedDelay(new Runnable() {
		
		@Override
		public void run() {
			try {
				a.mineBlock(bitCoinEngine.blockToBeMinned,difficulty);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	   }, 2,2, TimeUnit.SECONDS);
	  
	  ScheduledExecutorService mineBlockExecutorB = Executors.newScheduledThreadPool(1);
	  mineBlockExecutorB.scheduleWithFixedDelay(new Runnable() {
		
		@Override
		public void run() {
			try {
				b.mineBlock(bitCoinEngine.blockToBeMinned,difficulty);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	   }, 2,2, TimeUnit.SECONDS);
	  
	  
	  System.out.println(bitCoinEngine.unconfirmedTrasactions.size());
	  
  
	  ScheduledExecutorService executorForTransactions = Executors.newScheduledThreadPool(1);
	  executorForTransactions.scheduleWithFixedDelay(new Runnable() {
		
		@Override
		public void run() {
			a.sendBitcoin(b.getSendingAddress(), 10);
			b.sendBitcoin(c.getSendingAddress(), 15);
			c.sendBitcoin(a.getSendingAddress(), 20);
			d.sendBitcoin(b.getSendingAddress(), 20);
			a.sendBitcoin(c.getSendingAddress(), 20);
			e.sendBitcoin(f.getSendingAddress(), 20);
			
		}
	   }, 2,2, TimeUnit.SECONDS);
	  
	  
	  
	  System.out.println(bitCoinEngine.unconfirmedTrasactions.size());


	  
	  
	}

	private static boolean isBlockChainValid(List<Block> blockChain) {
		if (blockChain.size() > 1) {
			for (int i = 1; i <= blockChain.size()-1; i++) {
				Block currentBlock = blockChain.get(i-1);
				Block nextBlock = blockChain.get(i);
				if (!(nextBlock.previousHash.equals(currentBlock.currentHash))) {
					return false;
				}
			}
		}
		return true;
	}

	

}
