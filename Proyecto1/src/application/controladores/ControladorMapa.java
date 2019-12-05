package application.controladores;

import java.net.URL;
import java.util.ResourceBundle;

import com.lynden.gmapsfx.GoogleMapView;
import com.lynden.gmapsfx.MapComponentInitializedListener;
import com.lynden.gmapsfx.javascript.object.GoogleMap;
import com.lynden.gmapsfx.javascript.object.LatLong;
import com.lynden.gmapsfx.javascript.object.MapOptions;
import com.lynden.gmapsfx.javascript.object.MapTypeIdEnum;
import com.lynden.gmapsfx.javascript.object.Marker;
import com.lynden.gmapsfx.javascript.object.MarkerOptions;
import com.lynden.gmapsfx.service.geocoding.GeocoderStatus;
import com.lynden.gmapsfx.service.geocoding.GeocodingResult;
import com.lynden.gmapsfx.service.geocoding.GeocodingService;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

public class ControladorMapa implements Initializable, MapComponentInitializedListener {
	
    private GoogleMap map;
    
    private GeocodingService geocodingService;

    private StringProperty address = new SimpleStringProperty();
    
    @FXML
    private GoogleMapView mapView;
    
    @FXML
    private TextField addressTextField;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    	mapView.setKey("AIzaSyABUQnPXeldroN__fhm1LDiZh5sUtkSMBM"); // No usar 
        mapView.addMapInializedListener(this);
        address.bind(addressTextField.textProperty());
    } 
    
	@Override
	public void mapInitialized() {
	       geocodingService = new GeocodingService();
	        MapOptions mapOptions = new MapOptions();
	        
	        mapOptions.center(new LatLong(40.371830555556, -3.9189527777778))
	                .mapType(MapTypeIdEnum.ROADMAP)
	                .overviewMapControl(false)
	                .panControl(false)
	                .rotateControl(false)
	                .scaleControl(false)
	                .streetViewControl(false)
	                .zoomControl(false)
	                .zoom(16);

	        map = mapView.createMap(mapOptions);
	        
	        //Añadir un Marker al mapa
	        MarkerOptions markerOptions = new MarkerOptions();

	        markerOptions.position(new LatLong(40.371830555556, -3.9189527777778) )
	                    .visible(Boolean.TRUE)
	                    .title("My Marker");

	        Marker marker = new Marker( markerOptions );

	        map.addMarker(marker);
	}
    
    @FXML
    public void addressTextFieldAction(ActionEvent event) {
        geocodingService.geocode(address.get(), (GeocodingResult[] results, GeocoderStatus status) -> {
            
            LatLong latLong = null;
            
            if( status == GeocoderStatus.ZERO_RESULTS) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "No se encontraron direcciones coincidentes");
                alert.show();
                return;
            } else if( results.length > 1 ) {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Multiples resultados encontrados, mostrando el primero.");
                alert.show();
                latLong = new LatLong(results[0].getGeometry().getLocation().getLatitude(), results[0].getGeometry().getLocation().getLongitude());
            } else {
                latLong = new LatLong(results[0].getGeometry().getLocation().getLatitude(), results[0].getGeometry().getLocation().getLongitude());
            }
            
            map.setCenter(latLong);

        });
    }
    
}
