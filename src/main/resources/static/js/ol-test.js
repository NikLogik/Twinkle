function init(){
    var map = new ol.Map({
        target: 'map'
    });
    var osmLayer = new ol.layer.Tile({
        source: new ol.source.OSM()
    });
    map.addLayer(osmLayer);
    var view = new ol.View({
        center: [ 4188426.7147939987, 7508764.236877314 ],
        zoom: 12
    });
    map.setView(view);

    var overlay = new ol.Overlay({
        element: document.getElementById('overlay'),
        positioning: 'bottom-center'
    });

    var mousePosition = new ol.control.ScaleLine({
      units: 'metric',
      minWidth: 100
    });

    map.addControl(mousePosition);

    map.on('click', function(event) {
        // extract the spatial coordinate of the click event in map projection units
        var coord = event.coordinate;
        // transform it to decimal degrees
        var degrees = ol.proj.transform(coord, 'EPSG:3857', 'EPSG:4326');
        // format a human readable version
        var hdms = ol.coordinate.toStringHDMS(degrees);
        // update the overlay element's content
        var element = overlay.getElement();
        element.innerHTML = hdms;
        // position the element (using the coordinate in the map's projection)
        overlay.setPosition(coord);
        // and add it to the map
        map.addOverlay(overlay);
    });
}
