package GameOfLife.MVC.model.Entity;

import GameOfLife.MVC.controller.Enum.OfferState;

import javax.persistence.*;

/**
 * Created by kulandas on 22.08.2016.
 */
@Entity
public class Offer {
    @Id
    @GeneratedValue
    private int offerId;
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "offer")
    private Settings settings;
    private OfferState offerState;

    public Offer() {
    }

    public Offer(int offerId, Settings settings, OfferState offerState) {
        this.offerId = offerId;
        this.settings = settings;
        this.offerState = offerState;
    }

    public int getOfferId() {
        return offerId;
    }

    public void setOfferId(int offerId) {
        this.offerId = offerId;
    }

    public OfferState getOfferState() {
        return offerState;
    }

    public void setOfferState(OfferState offerState) {
        this.offerState = offerState;
    }

    public Settings getSettings() {
        return settings;
    }

    public void setSettings(Settings settings) {
        this.settings = settings;
    }
}
