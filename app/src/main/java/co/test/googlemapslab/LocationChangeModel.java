package co.test.googlemapslab;

class LocationChangeModel {
    public interface OnLocationChangeListener {
        void locationChanged();
    }

    private static LocationChangeModel locationChangeModel;
    private OnLocationChangeListener onLocationChangeListener;
    private LocationDto locationDto;

    public LocationChangeModel() {
    }

    public static LocationChangeModel getInstance() {
        if (locationChangeModel == null) {
            locationChangeModel = new LocationChangeModel();
        }
        return locationChangeModel;
    }

    public void setListener(OnLocationChangeListener onLocationChangeListener) {
        this.onLocationChangeListener = onLocationChangeListener;
    }

    public void changeLocation(LocationDto locationDto) {
        if (onLocationChangeListener != null) {
            this.locationDto = locationDto;
            notifyLocationChange();
        }
    }

    public LocationDto getState() {
        return locationDto;
    }

    private void notifyLocationChange() {
        onLocationChangeListener.locationChanged();
    }
}
