package fr.kaice.model.order;

public class Order {

	private int idMember;
	private int idProd;
	
	public Order(int idMember, int idProd) {
		this.idMember = idMember;
		this.idProd = idProd;
	}

	public int getIdMember() {
		return idMember;
	}

	public int getIdProd() {
		return idProd;
	}

	@Override
	protected Order clone() {
		return new Order(idMember, idProd);
	}

}
