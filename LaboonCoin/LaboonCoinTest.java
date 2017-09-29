import static org.junit.Assert.*;

import org.junit.*;

public class LaboonCoinTest {

    // Re-usable LaboonCoin reference.
    LaboonCoin _l;

    // Create a new LaboonCoin instance before each test.
    @Before
    public void setup() {
	_l = new LaboonCoin();
    }
    
    // Assert that creating a new LaboonCoin instance
    // does not return a null reference
    @Test
    public void testLaboonCoinExists() {
	assertNotNull(_l);
    }

    // Creating a block String with valid data should return
    // a valid block.  This includes printing data out
    // normally, the previous hash and current hash as a hex
    // representations of themself, and the nonce in hex
    // repretentation.  Values should be delimited by
    // pipes.
    @Test
    public void testCreateBlockValid() {
	int prevHash = 0x0;
	int nonce = 0x16f2;
	int hash = 0x545ac;
	String validBlock = _l.createBlock("kitten", prevHash, nonce, hash);
	assertEquals("kitten|00000000|000016f2|000545ac", validBlock);
    }

    // Trying to represent a blockchain which has no blocks
    // in it should just return an empty string.
    @Test
    public void testGetBlockChainNoElements() {
	// By default, _l.blockchain has no elements in it.
	// So we can just test it immediately.
	String blockChain = _l.getBlockChain();
	assertEquals("", blockChain);
    }
    

    // Viewing the blockchain as a full string which has valid
    // elements should include all of the elements.  Note that the
    // final element DOES have a trailing \n!
    @Test
    public void testGetBlockChainElements() {
	_l.blockchain.add("TESTBLOCK1|00000000|000010e9|000a3cd8");
	_l.blockchain.add("TESTBLOCK2|000a3cd8|00002ca8|0008ff30");
	_l.blockchain.add("TESTBLOCK3|0008ff30|00002171|0009f908");
	String blockChain = _l.getBlockChain();
	assertEquals("TESTBLOCK1|00000000|000010e9|000a3cd8\nTESTBLOCK2|000a3cd8|00002ca8|0008ff30\nTESTBLOCK3|0008ff30|00002171|0009f908\n", blockChain);
    }
	    
    // TODO - PUT YOUR SIX TESTS HERE
    @Test
    public void testHashingEmpty() {
        int actual = _l.hash("");

        assertEquals(actual, 10000000);
    }

    @Test
    public void testHashingLongStr() {

        String bigStr = "\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0"+
                        "\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0"+
                        "\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0";
        int actual = _l.hash(bigStr);

        assertEquals(0, actual);
    }

    @Test
    public void testEmptyStr() {
        int actual = _l.hash("");

        assertEquals(10000000, actual);
    }

    @Test
    public void whiteSpaceHash() {
        String whiteSpace = "    "; // tab: 9, spce: 32

        //(10000000*9) + 9 = 90000009
        //(90000009*32) + 32 = 2880000320
        int actual = _l.hash(whiteSpace);
        assertEquals(-1414966976, actual);
    }

    @Test
    public void failureTest() {
        int hash = 0x11111111;
        boolean fail = _l.validHash(1, hash);

        assertEquals(false, fail);
    }

    @Test
    public void negativeHashTest() {
        int neg = -1;
        boolean actual = _l.validHash(0, neg);

        assertEquals(true, actual);
    }

    @Test
    public void negativeDifficultyTest() {
        int num = 0x11111111;
        boolean actual = _l.validHash(-1, num);

        assertEquals(true, actual);
    }
}
