package engine;

public class promotionMove extends move{
	private String promotionPiece = "";
	move pMove;
	
	public void setPromotionPiece(String piece) {promotionPiece = piece;}
	public String getPromotionPiece() {return promotionPiece;}
	
	
	public promotionMove(move MOVE, String promotionPiece){
		super(MOVE.getStartSquareStr(), MOVE.getTargetSquareStr());
		this.pMove = MOVE;
		this.promotionPiece = promotionPiece;
	}
	
	@Override
	public String toString() { return pMove.getStartSquareStr() + " -> "+pMove.getTargetSquareStr()+" "+promotionPiece; }

}
