package engineByte;

public class bytePromotionMove extends byteMove{
	private byte promotionPiece = 0;
	byteMove pMove;
	
	public void setPromotionPiece(byte piece) {promotionPiece = piece;}
	public byte getPromotionPiece() {return promotionPiece;}
	
	
	public bytePromotionMove(byteMove MOVE, byte promotionPiece){
		super(MOVE.getStartSquareStr(), MOVE.getTargetSquareStr());
		this.pMove = MOVE;
		this.promotionPiece = promotionPiece;
	}
	
	@Override
	public String toString() { return pMove.getStartSquareStr() + " -> "+pMove.getTargetSquareStr()+" "+promotionPiece; }

}
