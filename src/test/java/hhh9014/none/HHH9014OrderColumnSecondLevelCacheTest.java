/*
 * Copyright 2014 JBoss Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package hhh9014.none;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.testing.junit4.BaseCoreFunctionalTestCase;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Burkhard Graves
 */
public class HHH9014OrderColumnSecondLevelCacheTest extends BaseCoreFunctionalTestCase {

    @Override
    protected Class[] getAnnotatedClasses() {
        return new Class[]{
            Parent.class,
            Child.class
        };
    }

    @Before
    public void before() {
        try (Session s = openSession()) {
            Transaction tx = s.beginTransaction();

            Parent parent = new Parent(1);
            parent.add(new Child(1)).add(new Child(2)).add(new Child(3));

            s.save(parent);
            tx.commit();
        }
    }

    @After
    public void after() {

        try (Session s = openSession()) {
            Transaction tx = s.beginTransaction();

            // delete parent and its children by cascade
            Parent parent = loadParentById(s, 1);
            s.delete(parent);

            tx.commit();
        }

        // empty second level cache
        sessionFactory().getCache().evictEntityRegions();
    }

    @Test
    public void hhh9014_123() throws Exception {
        try (Session s = openSession()) {
            Transaction tx = s.beginTransaction();

            // this way the test succeeds
//            Parent parent = loadParentById(s, 1);
//            Hibernate.initialize(parent.getChildren());

            assertChildHasIndex(loadChildById(s, 1), 0);
            assertChildHasIndex(loadChildById(s, 2), 1);
            assertChildHasIndex(loadChildById(s, 3), 2);

            tx.commit();
        }
    }

    @Test
	public void hhh9014_231() {

		reorderElements();

        try (Session s = openSession()) {
            Transaction tx = s.beginTransaction();

            // this way the test succeeds
//            Parent parent = loadParentById(s, 1);
//            Hibernate.initialize(parent.getChildren());

            assertChildHasIndex(loadChildById(s, 2), 0);
            assertChildHasIndex(loadChildById(s, 3), 1);
            assertChildHasIndex(loadChildById(s, 1), 2);
            
            tx.commit();
        }
	}

    private Parent loadParentById(Session s, int id) {
        return (Parent) s.load(Parent.class, id);
    }

    private Child loadChildById(Session s, int id) {
        return (Child) s.load(Child.class, id);
    }

    private void assertChildHasIndex(Child child, int index) {
        Assert.assertEquals(index, child.getIndex());
    }

    private void reorderElements() {

        try (Session s = openSession()) {
            Transaction tx = s.beginTransaction();

            Parent parent = loadParentById(s, 1);

            Hibernate.initialize(parent.getChildren());

            assertChildHasIndex(loadChildById(s, 1), 0);
            assertChildHasIndex(loadChildById(s, 2), 1);
            assertChildHasIndex(loadChildById(s, 3), 2);

            Child child = parent.getChildren().remove(0);
            parent.getChildren().add(child);

            s.update(parent);

            tx.commit();
        }
    }
}
