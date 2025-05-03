package descartes.info.l3p2.eyetrek.reconnaissanceEmpreintes.services.interfaces;

public interface IOnBackPressed {

    /**
     * In fragment: If you return true the back press will not be taken into account,
     * otherwise the activity will act naturally.
     * @return true if your processing has priority if not false.
     */
    boolean onBackPressed();
}
