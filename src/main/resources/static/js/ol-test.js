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
}
