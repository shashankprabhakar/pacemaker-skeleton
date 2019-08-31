package models;

import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static models.Fixture.activities;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class ActivityTest {
    Activity test = new Activity ("walk",  "bar", 4.2);

    @Test
    public void testCreate()
    {
        assertEquals ("walk",          test.type);
        assertEquals ("bar",        test.location);
        assertEquals (4.2,   test.distance);
    }

    @Test
    public void testIds()
    {
        Set<String> ids = new HashSet<>();
        for (Activity activity : activities)
        {
            ids.add(activity.id);
        }
        assertEquals (activities.size(), ids.size());
    }

    @Test
    public void testToString()
    {
        assertEquals ("Activity{" + "walk, bar, 4.2, []}", test.toString());
    }

    @Test
    public void testEquals()
    {
        Activity act = new Activity(activities.get(0).type, activities.get(0).location, activities.get(0).distance);
        assertEquals(act, activities.get(0));
        assertNotEquals( act, "str");


    }

}

