import java.util.Date;
import java.util.List;

public class Block {

	public String currentHash;
	public String previousHash;
	private List<Transaction> transactions; // our data will be a simple message.
	private long timeStamp; // as number of milliseconds since 1/1/1970.
	private int nonce;

	public boolean isBlockdMined = false;
	public String name;

	// Block Constructor.
	public Block(String name, List<Transaction> transactions, String previousHash) {
		this.transactions = transactions;
		this.name = name;
		this.previousHash = previousHash;
		this.timeStamp = new Date().getTime();
		this.currentHash = calculateHash();
	}

	public String calculateHash() {
		String calculatedhash = HashUtil
				.applySha256(previousHash + Long.toString(timeStamp) + Integer.toString(nonce) + transactions);
		return calculatedhash;
	}

	// Increases nonce value until hash target is reached.
	public void mineBlock(int difficulty, Account accountMinningBlock) {

		String target = new String(new char[difficulty]).replace('\0', '0');
		while (!currentHash.substring(0, difficulty).equals(target) && !isBlockdMined) {
			//System.out.println(accountMinningBlock.accountName +" -- minning "+ this.name);
			nonce++;
			currentHash = calculateHash();
		}
		// no other account mined it. this account mined the block
		if (currentHash.substring(0, difficulty).equals(target)) {
			//if (!isBlockdMined) {
				isBlockdMined = true;
				accountMinningBlock.isBlockMinnedWinner = true;
			//}
		}

	}

	@Override
	public boolean equals(Object obj) {

		Block other = (Block) obj;
		if (currentHash.equals(other.currentHash))
			return true;
		return false;
	}

	@Override
	public String toString() {
		return "Block [currentHash=" + currentHash + ", previousHash=" + previousHash + ", nonce=" + nonce
				+ ", isBlockdMined=" + isBlockdMined + ", name=" + name + "]";
	}

}