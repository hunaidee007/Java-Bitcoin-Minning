import java.util.ArrayList;
import java.util.List;

public class BitCoinEngine {

	public List<Block> blockChain = new ArrayList<Block>();

	private int difficulty = 2;

	public Block blockToBeMinned;

	public List<Transaction> unconfirmedTrasactions = new ArrayList<Transaction>();

	public void addTransaction(Transaction transaction) {
		unconfirmedTrasactions.add(transaction);

	}

	public void releaseBlock() {
		synchronized (this) {
			try {
				System.out.println(" unConfirmedTransaction size: " + unconfirmedTrasactions.size());
				System.out.println("blockChain.size() " + blockChain.size());
				if (blockChain.size() == 0) {
					blockToBeMinned = new Block(blockChain.size() + 1 + " - Block", unconfirmedTrasactions, "0");
				} else {
					if (blockToBeMinned == null) {
						blockToBeMinned = new Block(blockChain.size() + 1 + " - Block", unconfirmedTrasactions,
								blockChain.get(blockChain.size() - 1).currentHash);
					}

				}
				unconfirmedTrasactions.clear();
				System.out.println("-- Created block '" + blockToBeMinned.name + "' to be mined");
				this.notifyAll();

			} catch (Exception e) {
				e.printStackTrace();

			}
		}
	}

}
