import java.util.ArrayList;
import java.util.List;

public class Account {

	private List<Block> blockChain = new ArrayList<Block>();

	List<String> receivingAddresses = new ArrayList<String>();

	public boolean isBlockMinnedWinner = false;

	Block minningBlock;

	String sendingAddress;

	private Integer balance;

	private BitCoinEngine bitCoinEngine;

	public String accountName;

	public Account(BitCoinEngine bitCoinEngine, String accountname) {
		this.accountName = accountname;
		this.blockChain = bitCoinEngine.blockChain;
		this.bitCoinEngine = bitCoinEngine;
		this.receivingAddresses.add(HashUtil.applySha256(this.accountName));
	}

	public void sendBitcoin(String toAddress, Integer amount) {
		System.out.println(this.accountName + " sneding " + amount + " to " + toAddress);
		Transaction transaction = new Transaction(sendingAddress, toAddress, amount);
		bitCoinEngine.addTransaction(transaction);

	}

	public String getSendingAddress() {
		return receivingAddresses.get(0);
	}

	public void mineBlock(Block block, int difficulty) throws InterruptedException {
		synchronized (bitCoinEngine) {

			if (this.bitCoinEngine.blockChain == null) {
				this.bitCoinEngine.wait();
			}
			if (bitCoinEngine.blockToBeMinned != null && !bitCoinEngine.blockToBeMinned.isBlockdMined) {
				System.out.println(this.accountName + "-- Started minning block '" + block.name + "' with difficulty +"
						+ difficulty + "..");
				block.mineBlock(difficulty, this);

				if (isBlockMinnedWinner && !bitCoinEngine.blockChain.contains(bitCoinEngine.blockToBeMinned)) {
					System.out.println("|--------------------------------------------|");
					System.out
							.println("| " + this.accountName + "-- finished minning block '" + block.name + "'     |");
					System.out
							.println("| " + this.accountName + "-- adding Block '" + block.name + "' to BlockChain |");
					System.out.println("|--------------------------------------------|");

					bitCoinEngine.blockChain.add(block);
					bitCoinEngine.blockToBeMinned = null;

					for (Block b : bitCoinEngine.blockChain) {
						System.out.println(b);
					}
				} else {
					System.out.println(this.accountName + " failed to mine " + block.name);
				}

			} else {
				System.out.println(this.accountName + "-- Waiting for Block to be created..");
			}
		}

	}
}
