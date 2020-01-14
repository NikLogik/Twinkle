package ru.nachos.db.repository.fire;

import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.LineString;
import org.hibernate.spatial.JTSGeometryType;
import org.hibernate.spatial.dialect.postgis.PGGeometryTypeDescriptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;

@Component
public class IntersectionFilterRepositoryImpl implements IntersectionFilterRepository {

    private EntityManager entityManager;

    @Autowired
    public IntersectionFilterRepositoryImpl(@Qualifier("firesEntityManagerFactory") EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Geometry findIntersectionsByGeometry(LineString geometry, LineString geom) {
        return (Geometry) entityManager.createNativeQuery("select st_intersection(cast(?1 as geometry), cast(?2 as geometry)) as geo")
                .setParameter(1, geometry)
                .setParameter(2, geom)
                .unwrap(org.hibernate.query.NativeQuery.class)
                .addScalar("geo", new JTSGeometryType(PGGeometryTypeDescriptor.INSTANCE))
                .getSingleResult();
    }
}
