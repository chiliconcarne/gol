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
    private String offerGenerator;
    private OfferState offerState;

    public Offer() {
    }

    public Offer(int offerId, Settings settings, String offerGenerator, OfferState offerState) {
        this.offerId = offerId;
        this.settings = settings;
        this.offerGenerator = offerGenerator;
        this.offerState = offerState;
    }

    public int getOfferId() {
        return offerId;
    }

    public void setOfferId(int offerId) {
        this.offerId = offerId;
    }

    public Settings getSettings() {
        return settings;
    }

    public void setSettings(Settings settings) {
        this.settings = settings;
    }

    public String getOfferGenerator() {
        return offerGenerator;
    }

    public void setOfferGenerator(String offerGenerator) {
        this.offerGenerator = offerGenerator;
    }

    public OfferState getOfferState() {
        return offerState;
    }

    public void setOfferState(OfferState offerState) {
        this.offerState = offerState;
    }
}
