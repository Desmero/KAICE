package fr.kaice.model.order;

import fr.kaice.model.member.Member;
import fr.kaice.model.sell.SoldProduct;

/**
 * This class represent a order of only ONE item from a client. Those order ars paid, but not delivered yet.
 * If a client order multiple items, multiple instance are created. <br/>
 * <p>
 * It contains : the {@link Member} who make the order, and the {@link SoldProduct} buy by this member.
 *
 * @author Raphaël Merkling
 * @version 1.0
 */
public class Order {
    
    private final Member member;
    private final SoldProduct product;
    
    /**
     * Create a new {@link Order}.
     *
     * @param member  {@link Member} - The member client.
     * @param product {@link SoldProduct} - The product sold to the member.
     */
    public Order(Member member, SoldProduct product) {
        this.member = member;
        this.product = product;
    }
    
    /**
     * Return the {@link Member} client.
     *
     * @return The {@link Member} client.
     */
    public Member getMember() {
        return member;
    }
    
    /**
     * Return the {@link SoldProduct} sold to the member.
     *
     * @return The {@link SoldProduct} sold to the member.
     */
    public SoldProduct getProduct() {
        return product;
    }
    
    @Override
    protected Order clone() {
        return new Order(member, product);
    }
    
}
