/*
 * $Header: /home/jerenkrantz/tmp/commons/commons-convert/cvs/home/cvs/jakarta-commons//beanutils/src/test/org/apache/commons/beanutils/DynaBeanUtilsTestCase.java,v 1.21 2004/02/15 04:28:19 craigmcc Exp $
 * $Revision: 1.21 $
 * $Date: 2004/02/15 04:28:19 $
 *
 * ====================================================================
 * 
 * The Apache Software License, Version 1.1
 *
 * Copyright (c) 2001-2003 The Apache Software Foundation.  All rights
 * reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer. 
 *
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the
 *    distribution.
 *
 * 3. The end-user documentation included with the redistribution,
 *    if any, must include the following acknowledgement:  
 *       "This product includes software developed by the 
 *        Apache Software Foundation (http://www.apache.org/)."
 *    Alternately, this acknowledgement may appear in the software itself,
 *    if and wherever such third-party acknowledgements normally appear.
 *
 * 4. The names "Apache", "The Jakarta Project", "Commons", and "Apache Software
 *    Foundation" must not be used to endorse or promote products derived
 *    from this software without prior written permission. For written 
 *    permission, please contact apache@apache.org.
 *
 * 5. Products derived from this software may not be called "Apache",
 *    "Apache" nor may "Apache" appear in their names without prior 
 *    written permission of the Apache Software Foundation.
 *
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED.  IN NO EVENT SHALL THE APACHE SOFTWARE FOUNDATION OR
 * ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF
 * USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
 * OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 * ====================================================================
 *
 * This software consists of voluntary contributions made by many
 * individuals on behalf of the Apache Software Foundation.  For more
 * information on the Apache Software Foundation, please see
 * <http://www.apache.org/>.
 *
 */ 

package org.apache.commons.beanutils;


import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import junit.framework.TestCase;
import junit.framework.Test;
import junit.framework.TestSuite;


/**
 * Test case for BeanUtils when the underlying bean is actually a DynaBean.
 *
 * @author Craig R. McClanahan
 * @version $Revision: 1.21 $ $Date: 2004/02/15 04:28:19 $
 */

public class DynaBeanUtilsTestCase extends TestCase {


    // ----------------------------------------------------- Instance Variables


    /**
     * The basic test bean for each test.
     */
    protected DynaBean bean = null;


    /**
     * The nested bean pointed at by the "nested" property.
     */
    protected TestBean nested = null;


    /**
     * The set of properties that should be described.
     */
    protected String describes[] =
    { "booleanProperty",
      "booleanSecond",
      "byteProperty",
      "doubleProperty",
      "dupProperty",
      "floatProperty",
      "intArray",
      "intIndexed",
      "intProperty",
      "listIndexed",
      "longProperty",
      "mapProperty",
      "mappedProperty",
      "mappedIntProperty",
      "nested",
      "nullProperty",
      //      "readOnlyProperty",
      "shortProperty",
      "stringArray",
      "stringIndexed",
      "stringProperty"
    };


    // ----------------------------------------------------------- Constructors


    /**
     * Construct a new instance of this test case.
     *
     * @param name Name of the test case
     */
    public DynaBeanUtilsTestCase(String name) {

        super(name);

    }


    // --------------------------------------------------- Overall Test Methods


    /**
     * Set up instance variables required by this test case.
     */
    public void setUp() throws Exception {

        // Instantiate a new DynaBean instance
        DynaClass dynaClass = createDynaClass();
        bean = dynaClass.newInstance();

        // Initialize the DynaBean's property values (like TestBean)
        bean.set("booleanProperty", new Boolean(true));
        bean.set("booleanSecond", new Boolean(true));
        bean.set("byteProperty", new Byte((byte) 121));
        bean.set("doubleProperty", new Double(321.0));
        bean.set("floatProperty", new Float((float) 123.0));
        String dupProperty[] = { "Dup 0", "Dup 1", "Dup 2", "Dup 3", "Dup 4"};
        bean.set("dupProperty", dupProperty);
        int intArray[] = { 0, 10, 20, 30, 40 };
        bean.set("intArray", intArray);
        int intIndexed[] = { 0, 10, 20, 30, 40 };
        bean.set("intIndexed", intIndexed);
        bean.set("intProperty", new Integer(123));
        List listIndexed = new ArrayList();
        listIndexed.add("String 0");
        listIndexed.add("String 1");
        listIndexed.add("String 2");
        listIndexed.add("String 3");
        listIndexed.add("String 4");
        bean.set("listIndexed", listIndexed);
        bean.set("longProperty", new Long((long) 321));
        HashMap mapProperty = new HashMap();
        mapProperty.put("First Key", "First Value");
        mapProperty.put("Second Key", "Second Value");
        bean.set("mapProperty", mapProperty);
        HashMap mappedProperty = new HashMap();
        mappedProperty.put("First Key", "First Value");
        mappedProperty.put("Second Key", "Second Value");
        bean.set("mappedProperty", mappedProperty);
        HashMap mappedIntProperty = new HashMap();
        mappedIntProperty.put("One", new Integer(1));
        mappedIntProperty.put("Two", new Integer(2));
        bean.set("mappedIntProperty", mappedIntProperty);
        nested = new TestBean();
        bean.set("nested", nested);
        // Property "nullProperty" is not initialized, so it should return null
        bean.set("shortProperty", new Short((short) 987));
        String stringArray[] =
                { "String 0", "String 1", "String 2", "String 3", "String 4" };
        bean.set("stringArray", stringArray);
        String stringIndexed[] =
                { "String 0", "String 1", "String 2", "String 3", "String 4" };
        bean.set("stringIndexed", stringIndexed);
        bean.set("stringProperty", "This is a string");

    }


    /**
     * Return the tests included in this test suite.
     */
    public static Test suite() {

        return (new TestSuite(DynaBeanUtilsTestCase.class));

    }


    /**
     * Tear down instance variables required by this test case.
     */
    public void tearDown() {

        bean = null;
        nested = null;

    }



    // ------------------------------------------------ Individual Test Methods

    /**
     * Test the cloneBean() method from a DynaBean.
     */
    public void testCloneDynaBean() {

        // Set up an origin bean with customized properties
        DynaClass dynaClass = DynaBeanUtilsTestCase.createDynaClass();
        DynaBean orig = null;
        try {
            orig = dynaClass.newInstance();
        } catch (Exception e) {
            fail("newInstance(): " + e);
        }
        orig.set("booleanProperty", Boolean.FALSE);
        orig.set("byteProperty", new Byte((byte)111));
        orig.set("doubleProperty", new Double(333.33));
        orig.set("dupProperty", new String[] { "New 0", "New 1", "New 2" });
        orig.set("intArray", new int[] { 100, 200, 300 });
        orig.set("intProperty", new Integer(333));
        orig.set("longProperty", new Long(3333));
        orig.set("shortProperty", new Short((short) 33));
        orig.set("stringArray", new String[] { "New 0", "New 1" });
        orig.set("stringProperty", "Custom string");

        // Copy the origin bean to our destination test bean
        DynaBean clonedBean = null;
        try {
            clonedBean = (DynaBean) BeanUtils.cloneBean(orig);
        } catch (Exception e) {
            fail("Threw exception: " + e);
        }

        // Validate the results for scalar properties
        assertEquals("Cloned boolean property",
                     false,
                     ((Boolean) clonedBean.get("booleanProperty")).booleanValue());
        assertEquals("Cloned byte property",
                     (byte) 111,
                     ((Byte) clonedBean.get("byteProperty")).byteValue());
        assertEquals("Cloned double property",
                     333.33,
                     ((Double) clonedBean.get("doubleProperty")).doubleValue(),
                     0.005);
        assertEquals("Cloned int property",
                     333,
                     ((Integer) clonedBean.get("intProperty")).intValue());
        assertEquals("Cloned long property",
                     (long) 3333,
                     ((Long) clonedBean.get("longProperty")).longValue());
        assertEquals("Cloned short property",
                     (short) 33,
                     ((Short) clonedBean.get("shortProperty")).shortValue());
        assertEquals("Cloned string property",
                     "Custom string",
                     (String) clonedBean.get("stringProperty"));

        // Validate the results for array properties
        String dupProperty[] = (String[]) clonedBean.get("dupProperty");
        assertNotNull("dupProperty present", dupProperty);
        assertEquals("dupProperty length", 3, dupProperty.length);
        assertEquals("dupProperty[0]", "New 0", dupProperty[0]);
        assertEquals("dupProperty[1]", "New 1", dupProperty[1]);
        assertEquals("dupProperty[2]", "New 2", dupProperty[2]);
        int intArray[] = (int[]) clonedBean.get("intArray");
        assertNotNull("intArray present", intArray);
        assertEquals("intArray length", 3, intArray.length);
        assertEquals("intArray[0]", 100, intArray[0]);
        assertEquals("intArray[1]", 200, intArray[1]);
        assertEquals("intArray[2]", 300, intArray[2]);
        String stringArray[] = (String[]) clonedBean.get("stringArray");
        assertNotNull("stringArray present", stringArray);
        assertEquals("stringArray length", 2, stringArray.length);
        assertEquals("stringArray[0]", "New 0", stringArray[0]);
        assertEquals("stringArray[1]", "New 1", stringArray[1]);

    }

    /**
     * Test the copyProperties() method from a DynaBean.
     */
    public void testCopyPropertiesDynaBean() {

        // Set up an origin bean with customized properties
        DynaClass dynaClass = DynaBeanUtilsTestCase.createDynaClass();
        DynaBean orig = null;
        try {
            orig = dynaClass.newInstance();
        } catch (Exception e) {
            fail("newInstance(): " + e);
        }
        orig.set("booleanProperty", Boolean.FALSE);
        orig.set("byteProperty", new Byte((byte)111));
        orig.set("doubleProperty", new Double(333.33));
        orig.set("dupProperty", new String[] { "New 0", "New 1", "New 2" });
        orig.set("intArray", new int[] { 100, 200, 300 });
        orig.set("intProperty", new Integer(333));
        orig.set("longProperty", new Long(3333));
        orig.set("shortProperty", new Short((short) 33));
        orig.set("stringArray", new String[] { "New 0", "New 1" });
        orig.set("stringProperty", "Custom string");

        // Copy the origin bean to our destination test bean
        try {
            BeanUtils.copyProperties(bean, orig);
        } catch (Exception e) {
            fail("Threw exception: " + e);
        }

        // Validate the results for scalar properties
        assertEquals("Copied boolean property",
                     false,
                     ((Boolean) bean.get("booleanProperty")).booleanValue());
        assertEquals("Copied byte property",
                     (byte) 111,
                     ((Byte) bean.get("byteProperty")).byteValue());
        assertEquals("Copied double property",
                     333.33,
                     ((Double) bean.get("doubleProperty")).doubleValue(),
                     0.005);
        assertEquals("Copied int property",
                     333,
                     ((Integer) bean.get("intProperty")).intValue());
        assertEquals("Copied long property",
                     (long) 3333,
                     ((Long) bean.get("longProperty")).longValue());
        assertEquals("Copied short property",
                     (short) 33,
                     ((Short) bean.get("shortProperty")).shortValue());
        assertEquals("Copied string property",
                     "Custom string",
                     (String) bean.get("stringProperty"));

        // Validate the results for array properties
        String dupProperty[] = (String[]) bean.get("dupProperty");
        assertNotNull("dupProperty present", dupProperty);
        assertEquals("dupProperty length", 3, dupProperty.length);
        assertEquals("dupProperty[0]", "New 0", dupProperty[0]);
        assertEquals("dupProperty[1]", "New 1", dupProperty[1]);
        assertEquals("dupProperty[2]", "New 2", dupProperty[2]);
        int intArray[] = (int[]) bean.get("intArray");
        assertNotNull("intArray present", intArray);
        assertEquals("intArray length", 3, intArray.length);
        assertEquals("intArray[0]", 100, intArray[0]);
        assertEquals("intArray[1]", 200, intArray[1]);
        assertEquals("intArray[2]", 300, intArray[2]);
        String stringArray[] = (String[]) bean.get("stringArray");
        assertNotNull("stringArray present", stringArray);
        assertEquals("stringArray length", 2, stringArray.length);
        assertEquals("stringArray[0]", "New 0", stringArray[0]);
        assertEquals("stringArray[1]", "New 1", stringArray[1]);

    }


    /**
     * Test copyProperties() when the origin is a a <code>Map</code>.
     */
    public void testCopyPropertiesMap() {

        Map map = new HashMap();
        map.put("booleanProperty", "false");
        map.put("byteProperty", "111");
        map.put("doubleProperty", "333.0");
        map.put("dupProperty", new String[] { "New 0", "New 1", "New 2" });
        map.put("floatProperty", "222.0");
        map.put("intArray", new String[] { "0", "100", "200" });
        map.put("intProperty", "111");
        map.put("longProperty", "444");
        map.put("shortProperty", "555");
        map.put("stringProperty", "New String Property");

        try {
            BeanUtils.copyProperties(bean, map);
        } catch (Throwable t) {
            fail("Threw " + t.toString());
        }

        // Scalar properties
        assertEquals("booleanProperty", false,
                     ((Boolean) bean.get("booleanProperty")).booleanValue());
        assertEquals("byteProperty", (byte) 111,
                     ((Byte) bean.get("byteProperty")).byteValue());
        assertEquals("doubleProperty", 333.0,
                     ((Double) bean.get("doubleProperty")).doubleValue(),
                     0.005);
        assertEquals("floatProperty", (float) 222.0,
                     ((Float) bean.get("floatProperty")).floatValue(),
                     (float) 0.005);
        assertEquals("intProperty", 111,
                     ((Integer) bean.get("intProperty")).intValue());
        assertEquals("longProperty", (long) 444,
                     ((Long) bean.get("longProperty")).longValue());
        assertEquals("shortProperty", (short) 555,
                     ((Short) bean.get("shortProperty")).shortValue());
        assertEquals("stringProperty", "New String Property",
                     (String) bean.get("stringProperty"));

        // Indexed Properties
        String dupProperty[] = (String[]) bean.get("dupProperty");
        assertNotNull("dupProperty present", dupProperty);
        assertEquals("dupProperty length", 3, dupProperty.length);
        assertEquals("dupProperty[0]", "New 0", dupProperty[0]);
        assertEquals("dupProperty[1]", "New 1", dupProperty[1]);
        assertEquals("dupProperty[2]", "New 2", dupProperty[2]);
        int intArray[] = (int[]) bean.get("intArray");
        assertNotNull("intArray present", intArray);
        assertEquals("intArray length", 3, intArray.length);
        assertEquals("intArray[0]", 0, intArray[0]);
        assertEquals("intArray[1]", 100, intArray[1]);
        assertEquals("intArray[2]", 200, intArray[2]);

    }


    /**
     * Test the copyProperties() method from a standard JavaBean.
     */
    public void testCopyPropertiesStandard() {

        // Set up an origin bean with customized properties
        TestBean orig = new TestBean();
        orig.setBooleanProperty(false);
        orig.setByteProperty((byte) 111);
        orig.setDoubleProperty(333.33);
        orig.setDupProperty(new String[] { "New 0", "New 1", "New 2" });
        orig.setIntArray(new int[] { 100, 200, 300 });
        orig.setIntProperty(333);
        orig.setLongProperty(3333);
        orig.setShortProperty((short) 33);
        orig.setStringArray(new String[] { "New 0", "New 1" });
        orig.setStringProperty("Custom string");

        // Copy the origin bean to our destination test bean
        try {
            BeanUtils.copyProperties(bean, orig);
        } catch (Exception e) {
            fail("Threw exception: " + e);
        }

        // Validate the results for scalar properties
        assertEquals("Copied boolean property",
                     false,
                     ((Boolean) bean.get("booleanProperty")).booleanValue());
        assertEquals("Copied byte property",
                     (byte) 111,
                     ((Byte) bean.get("byteProperty")).byteValue());
        assertEquals("Copied double property",
                     333.33,
                     ((Double) bean.get("doubleProperty")).doubleValue(),
                     0.005);
        assertEquals("Copied int property",
                     333,
                     ((Integer) bean.get("intProperty")).intValue());
        assertEquals("Copied long property",
                     (long) 3333,
                     ((Long) bean.get("longProperty")).longValue());
        assertEquals("Copied short property",
                     (short) 33,
                     ((Short) bean.get("shortProperty")).shortValue());
        assertEquals("Copied string property",
                     "Custom string",
                     (String) bean.get("stringProperty"));

        // Validate the results for array properties
        String dupProperty[] = (String[]) bean.get("dupProperty");
        assertNotNull("dupProperty present", dupProperty);
        assertEquals("dupProperty length", 3, dupProperty.length);
        assertEquals("dupProperty[0]", "New 0", dupProperty[0]);
        assertEquals("dupProperty[1]", "New 1", dupProperty[1]);
        assertEquals("dupProperty[2]", "New 2", dupProperty[2]);
        int intArray[] = (int[]) bean.get("intArray");
        assertNotNull("intArray present", intArray);
        assertEquals("intArray length", 3, intArray.length);
        assertEquals("intArray[0]", 100, intArray[0]);
        assertEquals("intArray[1]", 200, intArray[1]);
        assertEquals("intArray[2]", 300, intArray[2]);
        String stringArray[] = (String[]) bean.get("stringArray");
        assertNotNull("stringArray present", stringArray);
        assertEquals("stringArray length", 2, stringArray.length);
        assertEquals("stringArray[0]", "New 0", stringArray[0]);
        assertEquals("stringArray[1]", "New 1", stringArray[1]);

    }


    /**
     * Test the describe() method.
     */
    public void testDescribe() {

        Map map = null;
        try {
            map = PropertyUtils.describe(bean);
        } catch (Exception e) {
            fail("Threw exception " + e);
        }

        // Verify existence of all the properties that should be present
        for (int i = 0; i < describes.length; i++) {
            assertTrue("Property '" + describes[i] + "' is present",
                       map.containsKey(describes[i]));
        }
        assertTrue("Property 'writeOnlyProperty' is not present",
                   !map.containsKey("writeOnlyProperty"));

        // Verify the values of scalar properties
        assertEquals("Value of 'booleanProperty'",
                     Boolean.TRUE,
                     (Boolean) map.get("booleanProperty"));
        assertEquals("Value of 'byteProperty'",
                     new Byte((byte) 121),
                     (Byte) map.get("byteProperty"));
        assertEquals("Value of 'doubleProperty'",
                     new Double(321.0),
                     (Double) map.get("doubleProperty"));
        assertEquals("Value of 'floatProperty'",
                     new Float((float) 123.0),
                     (Float) map.get("floatProperty"));
        assertEquals("Value of 'intProperty'",
                     new Integer(123),
                     (Integer) map.get("intProperty"));
        assertEquals("Value of 'longProperty'",
                     new Long(321),
                     (Long) map.get("longProperty"));
        assertEquals("Value of 'shortProperty'",
                     new Short((short) 987),
                     (Short) map.get("shortProperty"));
        assertEquals("Value of 'stringProperty'",
                     "This is a string",
                     (String) map.get("stringProperty"));

    }


    /**
     * Test populate() method on array properties as a whole.
     */
    public void testPopulateArrayProperties() {

        try {

            HashMap map = new HashMap();
            //            int intArray[] = new int[] { 123, 456, 789 };
            String intArrayIn[] = new String[] { "123", "456", "789" };
            map.put("intArray", intArrayIn);
            String stringArray[] = new String[]
                { "New String 0", "New String 1" };
            map.put("stringArray", stringArray);

            BeanUtils.populate(bean, map);

            int intArray[] = (int[]) bean.get("intArray");
            assertNotNull("intArray is present", intArray);
            assertEquals("intArray length",
                         3, intArray.length);
            assertEquals("intArray[0]", 123, intArray[0]);
            assertEquals("intArray[1]", 456, intArray[1]);
            assertEquals("intArray[2]", 789, intArray[2]);
            stringArray = (String[]) bean.get("stringArray");
            assertNotNull("stringArray is present", stringArray);
            assertEquals("stringArray length", 2, stringArray.length);
            assertEquals("stringArray[0]", "New String 0", stringArray[0]);
            assertEquals("stringArray[1]", "New String 1", stringArray[1]);

        } catch (IllegalAccessException e) {
            fail("IllegalAccessException");
        } catch (InvocationTargetException e) {
            fail("InvocationTargetException");
        }

    }


    /**
     *  tests the string and int arrays of TestBean
     */
    public void testGetArrayProperty() {
        try {
            String arr[] = BeanUtils.getArrayProperty(bean, "stringArray");
            String comp[] = (String[]) bean.get("stringArray");

            assertTrue("String array length = " + comp.length,
                    (comp.length == arr.length));

            arr = BeanUtils.getArrayProperty(bean, "intArray");
            int iarr[] = (int[]) bean.get("intArray");

            assertTrue("String array length = " + iarr.length,
                    (iarr.length == arr.length));
        } catch (IllegalAccessException e) {
            fail("IllegalAccessException");
        } catch (InvocationTargetException e) {
            fail("InvocationTargetException");
        } catch (NoSuchMethodException e) {
            fail("NoSuchMethodException");
        }

    }


    /**
     *  tests getting an indexed property
     */
    public void testGetIndexedProperty1() {
        try {
            String val = BeanUtils.getIndexedProperty(bean, "intIndexed[3]");
            String comp = String.valueOf(bean.get("intIndexed", 3));
            assertTrue("intIndexed[3] == " + comp, val.equals(comp));

            val = BeanUtils.getIndexedProperty(bean, "stringIndexed[3]");
            comp = (String) bean.get("stringIndexed", 3);
            assertTrue("stringIndexed[3] == " + comp, val.equals(comp));
        } catch (IllegalAccessException e) {
            fail("IllegalAccessException");
        } catch (InvocationTargetException e) {
            fail("InvocationTargetException");
        } catch (NoSuchMethodException e) {
            fail("NoSuchMethodException");
        }
    }


    /**
     *  tests getting an indexed property
     */
    public void testGetIndexedProperty2() {
        try {
            String val = BeanUtils.getIndexedProperty(bean, "intIndexed", 3);
            String comp = String.valueOf(bean.get("intIndexed", 3));

            assertTrue("intIndexed,3 == " + comp, val.equals(comp));

            val = BeanUtils.getIndexedProperty(bean, "stringIndexed", 3);
            comp = (String) bean.get("stringIndexed", 3);

            assertTrue("stringIndexed,3 == " + comp, val.equals(comp));

        } catch (IllegalAccessException e) {
            fail("IllegalAccessException");
        } catch (InvocationTargetException e) {
            fail("InvocationTargetException");
        } catch (NoSuchMethodException e) {
            fail("NoSuchMethodException");
        }
    }


    /**
     *  tests getting a nested property
     */
    public void testGetNestedProperty() {
        try {
            String val = BeanUtils.getNestedProperty(bean, "nested.stringProperty");
            String comp = nested.getStringProperty();
            assertTrue("nested.StringProperty == " + comp,
                    val.equals(comp));
        } catch (IllegalAccessException e) {
            fail("IllegalAccessException");
        } catch (InvocationTargetException e) {
            fail("InvocationTargetException");
        } catch (NoSuchMethodException e) {
            fail("NoSuchMethodException");
        }
    }


    /**
     *  tests getting a 'whatever' property
     */
    public void testGetGeneralProperty() {
        try {
            String val = BeanUtils.getProperty(bean, "nested.intIndexed[2]");
            String comp = String.valueOf(bean.get("intIndexed", 2));

            assertTrue("nested.intIndexed[2] == " + comp,
                    val.equals(comp));
        } catch (IllegalAccessException e) {
            fail("IllegalAccessException");
        } catch (InvocationTargetException e) {
            fail("InvocationTargetException");
        } catch (NoSuchMethodException e) {
            fail("NoSuchMethodException");
        }
    }


    /**
     *  tests getting a 'whatever' property
     */
    public void testGetSimpleProperty() {
        try {
            String val = BeanUtils.getSimpleProperty(bean, "shortProperty");
            String comp = String.valueOf(bean.get("shortProperty"));

            assertTrue("shortProperty == " + comp,
                    val.equals(comp));
        } catch (IllegalAccessException e) {
            fail("IllegalAccessException");
        } catch (InvocationTargetException e) {
            fail("InvocationTargetException");
        } catch (NoSuchMethodException e) {
            fail("NoSuchMethodException");
        }
    }


    /**
     * Test populate() method on individual array elements.
     */
    public void testPopulateArrayElements() {

        try {

            HashMap map = new HashMap();
            map.put("intIndexed[0]", "100");
            map.put("intIndexed[2]", "120");
            map.put("intIndexed[4]", "140");

            BeanUtils.populate(bean, map);
            Integer intIndexed0 = (Integer) bean.get("intIndexed", 0);
            assertEquals("intIndexed[0] is 100",
                         100, intIndexed0.intValue());
            Integer intIndexed1 = (Integer) bean.get("intIndexed", 1);
            assertEquals("intIndexed[1] is 10",
                         10, intIndexed1.intValue());
            Integer intIndexed2 = (Integer) bean.get("intIndexed", 2);
            assertEquals("intIndexed[2] is 120",
                         120, intIndexed2.intValue());
            Integer intIndexed3 = (Integer) bean.get("intIndexed", 3);
            assertEquals("intIndexed[3] is 30",
                         30, intIndexed3.intValue());
            Integer intIndexed4 = (Integer) bean.get("intIndexed", 4);
            assertEquals("intIndexed[4] is 140",
                         140, intIndexed4.intValue());

            map.clear();
            map.put("stringIndexed[1]", "New String 1");
            map.put("stringIndexed[3]", "New String 3");

            BeanUtils.populate(bean, map);

            assertEquals("stringIndexed[0] is \"String 0\"",
                         "String 0",
                         (String) bean.get("stringIndexed", 0));
            assertEquals("stringIndexed[1] is \"New String 1\"",
                         "New String 1",
                         (String) bean.get("stringIndexed", 1));
            assertEquals("stringIndexed[2] is \"String 2\"",
                         "String 2",
                         (String) bean.get("stringIndexed", 2));
            assertEquals("stringIndexed[3] is \"New String 3\"",
                         "New String 3",
                         (String) bean.get("stringIndexed", 3));
            assertEquals("stringIndexed[4] is \"String 4\"",
                         "String 4",
                         (String) bean.get("stringIndexed", 4));

        } catch (IllegalAccessException e) {
            fail("IllegalAccessException");
        } catch (InvocationTargetException e) {
            fail("InvocationTargetException");
        }

    }


    /**
     * Test populate() on mapped properties.
     */
    public void testPopulateMapped() {

        try {

            HashMap map = new HashMap();
            map.put("mappedProperty(First Key)", "New First Value");
            map.put("mappedProperty(Third Key)", "New Third Value");

            BeanUtils.populate(bean, map);

            assertEquals("mappedProperty(First Key)",
                         "New First Value",
                         (String) bean.get("mappedProperty", "First Key"));
            assertEquals("mappedProperty(Second Key)",
                         "Second Value",
                         (String) bean.get("mappedProperty", "Second Key"));
            assertEquals("mappedProperty(Third Key)",
                         "New Third Value",
                         (String) bean.get("mappedProperty", "Third Key"));
            assertNull("mappedProperty(Fourth Key",
                       (String) bean.get("mappedProperty", "Fourth Key"));

        } catch (IllegalAccessException e) {
            fail("IllegalAccessException");
        } catch (InvocationTargetException e) {
            fail("InvocationTargetException");
        }

    }


    /**
     * Test populate() method on nested properties.
     */
    public void testPopulateNested() {

        try {

            HashMap map = new HashMap();
            map.put("nested.booleanProperty", "false");
            // booleanSecond is left at true
            map.put("nested.doubleProperty", "432.0");
            // floatProperty is left at 123.0
            map.put("nested.intProperty", "543");
            // longProperty is left at 321
            map.put("nested.shortProperty", "654");
            // stringProperty is left at "This is a string"

            BeanUtils.populate(bean, map);

            TestBean nested = (TestBean) bean.get("nested");
            assertTrue("booleanProperty is false",
                       !nested.getBooleanProperty());
            assertTrue("booleanSecond is true",
                       nested.isBooleanSecond());
            assertEquals("doubleProperty is 432.0",
                         (double) 432.0,
                         nested.getDoubleProperty(),
                         (double) 0.005);
            assertEquals("floatProperty is 123.0",
                         (float) 123.0,
                         nested.getFloatProperty(),
                         (float) 0.005);
            assertEquals("intProperty is 543",
                         543, nested.getIntProperty());
            assertEquals("longProperty is 321",
                         (long) 321, nested.getLongProperty());
            assertEquals("shortProperty is 654",
                         (short) 654, nested.getShortProperty());
            assertEquals("stringProperty is \"This is a string\"",
                         "This is a string",
                         nested.getStringProperty());

        } catch (IllegalAccessException e) {
            fail("IllegalAccessException");
        } catch (InvocationTargetException e) {
            fail("InvocationTargetException");
        }

    }


    /**
     * Test populate() method on scalar properties.
     */
    public void testPopulateScalar() {

        try {

            bean.set("nullProperty", "non-null value");

            HashMap map = new HashMap();
            map.put("booleanProperty", "false");
            // booleanSecond is left at true
            map.put("doubleProperty", "432.0");
            // floatProperty is left at 123.0
            map.put("intProperty", "543");
            // longProperty is left at 321
            map.put("nullProperty", null);
            map.put("shortProperty", "654");
            // stringProperty is left at "This is a string"

            BeanUtils.populate(bean, map);

            Boolean booleanProperty = (Boolean) bean.get("booleanProperty");
            assertTrue("booleanProperty is false", !booleanProperty.booleanValue());
            Boolean booleanSecond = (Boolean) bean.get("booleanSecond");
            assertTrue("booleanSecond is true", booleanSecond.booleanValue());
            Double doubleProperty = (Double) bean.get("doubleProperty");
            assertEquals("doubleProperty is 432.0",
                         (double) 432.0, doubleProperty.doubleValue(),
                         (double) 0.005);
            Float floatProperty = (Float) bean.get("floatProperty");
            assertEquals("floatProperty is 123.0",
                         (float) 123.0, floatProperty.floatValue(),
                         (float) 0.005);
            Integer intProperty = (Integer) bean.get("intProperty");
            assertEquals("intProperty is 543",
                         543, intProperty.intValue());
            Long longProperty = (Long) bean.get("longProperty");
            assertEquals("longProperty is 321",
                         (long) 321, longProperty.longValue());
            assertNull("nullProperty is null", bean.get("nullProperty"));
            Short shortProperty = (Short) bean.get("shortProperty");
            assertEquals("shortProperty is 654",
                         (short) 654, shortProperty.shortValue());
            assertEquals("stringProperty is \"This is a string\"",
                         "This is a string",
                         (String) bean.get("stringProperty"));

        } catch (IllegalAccessException e) {
            fail("IllegalAccessException");
        } catch (InvocationTargetException e) {
            fail("InvocationTargetException");
        }

    }


    /**
     * Test calling setProperty() with null property values.
     */
    public void testSetPropertyNullValues() throws Exception {

        Object oldValue = null;
        Object newValue = null;

        // Scalar value into array
        oldValue = PropertyUtils.getSimpleProperty(bean, "stringArray");
        BeanUtils.setProperty(bean, "stringArray", (String) null);
        newValue = PropertyUtils.getSimpleProperty(bean, "stringArray");
        assertNotNull("stringArray is not null", newValue);
        assertTrue("stringArray of correct type",
                   newValue instanceof String[]);
        assertEquals("stringArray length",
                     1, ((String[]) newValue).length);
        assertTrue("stringArray[0] is null",
                   ((String[]) newValue)[0] == null);
        PropertyUtils.setProperty(bean, "stringArray", oldValue);

        // Indexed value into array
        oldValue = PropertyUtils.getSimpleProperty(bean, "stringArray");
        BeanUtils.setProperty(bean, "stringArray[2]", (String) null);
        newValue = PropertyUtils.getSimpleProperty(bean, "stringArray");
        assertNotNull("stringArray is not null", newValue);
        assertTrue("stringArray of correct type",
                   newValue instanceof String[]);
        assertEquals("stringArray length",
                     5, ((String[]) newValue).length);
        assertTrue("stringArray[2] is null",
                   ((String[]) newValue)[2] == null);
        PropertyUtils.setProperty(bean, "stringArray", oldValue);

        // Value into scalar
        BeanUtils.setProperty(bean, "stringProperty", null);
        assertTrue("stringProperty is now null",
                   BeanUtils.getProperty(bean, "stringProperty") == null);

    }


    /**
     * Test converting to and from primitive wrapper types.
     */
    public void testSetPropertyOnPrimitiveWrappers() throws Exception {

        BeanUtils.setProperty(bean,"intProperty", new Integer(1));
        assertEquals(1,((Integer) bean.get("intProperty")).intValue());
        BeanUtils.setProperty(bean,"stringProperty", new Integer(1));
        assertEquals(1, Integer.parseInt((String) bean.get("stringProperty")));

    }


    /**
     * Test setting a null property value.
     */
    public void testSetPropertyNull() throws Exception {

        bean.set("nullProperty", "non-null value");
        BeanUtils.setProperty(bean, "nullProperty", null);
        assertNull("nullProperty is null", bean.get("nullProperty"));

    }


    /**
     * Test narrowing and widening conversions on byte.
     */
    public void testCopyPropertyByte() throws Exception {

        BeanUtils.setProperty(bean, "byteProperty", new Byte((byte) 123));
        assertEquals((byte) 123, ((Byte) bean.get("byteProperty")).byteValue());
/*
        BeanUtils.setProperty(bean, "byteProperty", new Double((double) 123));
        assertEquals((byte) 123, ((Byte) bean.get("byteProperty")).byteValue());
        BeanUtils.setProperty(bean, "byteProperty", new Float((float) 123));
        assertEquals((byte) 123, ((Byte) bean.get("byteProperty")).byteValue());
*/
        BeanUtils.setProperty(bean, "byteProperty", new Integer((int) 123));
        assertEquals((byte) 123, ((Byte) bean.get("byteProperty")).byteValue());
        BeanUtils.setProperty(bean, "byteProperty", new Long((long) 123));
        assertEquals((byte) 123, ((Byte) bean.get("byteProperty")).byteValue());
        BeanUtils.setProperty(bean, "byteProperty", new Short((short) 123));
        assertEquals((byte) 123, ((Byte) bean.get("byteProperty")).byteValue());

    }


    /**
     * Test narrowing and widening conversions on double.
     */
    public void testCopyPropertyDouble() throws Exception {

        BeanUtils.setProperty(bean, "doubleProperty", new Byte((byte) 123));
        assertEquals((double) 123, ((Double) bean.get("doubleProperty")).doubleValue(), 0.005);
        BeanUtils.setProperty(bean, "doubleProperty", new Double((double) 123));
        assertEquals((double) 123, ((Double) bean.get("doubleProperty")).doubleValue(), 0.005);
        BeanUtils.setProperty(bean, "doubleProperty", new Float((float) 123));
        assertEquals((double) 123, ((Double) bean.get("doubleProperty")).doubleValue(), 0.005);
        BeanUtils.setProperty(bean, "doubleProperty", new Integer((int) 123));
        assertEquals((double) 123, ((Double) bean.get("doubleProperty")).doubleValue(), 0.005);
        BeanUtils.setProperty(bean, "doubleProperty", new Long((long) 123));
        assertEquals((double) 123, ((Double) bean.get("doubleProperty")).doubleValue(), 0.005);
        BeanUtils.setProperty(bean, "doubleProperty", new Short((short) 123));
        assertEquals((double) 123, ((Double) bean.get("doubleProperty")).doubleValue(), 0.005);

    }


    /**
     * Test narrowing and widening conversions on float.
     */
    public void testCopyPropertyFloat() throws Exception {

        BeanUtils.setProperty(bean, "floatProperty", new Byte((byte) 123));
        assertEquals((float) 123, ((Float) bean.get("floatProperty")).floatValue(), 0.005);
        BeanUtils.setProperty(bean, "floatProperty", new Double((double) 123));
        assertEquals((float) 123, ((Float) bean.get("floatProperty")).floatValue(), 0.005);
        BeanUtils.setProperty(bean, "floatProperty", new Float((float) 123));
        assertEquals((float) 123, ((Float) bean.get("floatProperty")).floatValue(), 0.005);
        BeanUtils.setProperty(bean, "floatProperty", new Integer((int) 123));
        assertEquals((float) 123, ((Float) bean.get("floatProperty")).floatValue(), 0.005);
        BeanUtils.setProperty(bean, "floatProperty", new Long((long) 123));
        assertEquals((float) 123, ((Float) bean.get("floatProperty")).floatValue(), 0.005);
        BeanUtils.setProperty(bean, "floatProperty", new Short((short) 123));
        assertEquals((float) 123, ((Float) bean.get("floatProperty")).floatValue(), 0.005);

    }


    /**
     * Test narrowing and widening conversions on int.
     */
    public void testCopyPropertyInteger() throws Exception {

        BeanUtils.setProperty(bean, "longProperty", new Byte((byte) 123));
        assertEquals((int) 123, ((Integer) bean.get("intProperty")).intValue());
/*
        BeanUtils.setProperty(bean, "longProperty", new Double((double) 123));
        assertEquals((int) 123, ((Integer) bean.get("intProperty")).intValue());
        BeanUtils.setProperty(bean, "longProperty", new Float((float) 123));
        assertEquals((int) 123, ((Integer) bean.get("intProperty")).intValue());
*/
        BeanUtils.setProperty(bean, "longProperty", new Integer((int) 123));
        assertEquals((int) 123, ((Integer) bean.get("intProperty")).intValue());
        BeanUtils.setProperty(bean, "longProperty", new Long((long) 123));
        assertEquals((int) 123, ((Integer) bean.get("intProperty")).intValue());
        BeanUtils.setProperty(bean, "longProperty", new Short((short) 123));
        assertEquals((int) 123, ((Integer) bean.get("intProperty")).intValue());

    }


    /**
     * Test narrowing and widening conversions on long.
     */
    public void testCopyPropertyLong() throws Exception {

        BeanUtils.setProperty(bean, "longProperty", new Byte((byte) 123));
        assertEquals((long) 123, ((Long) bean.get("longProperty")).longValue());
/*
        BeanUtils.setProperty(bean, "longProperty", new Double((double) 123));
        assertEquals((long) 123, ((Long) bean.get("longProperty")).longValue());
        BeanUtils.setProperty(bean, "longProperty", new Float((float) 123));
        assertEquals((long) 123, ((Long) bean.get("longProperty")).longValue());
*/
        BeanUtils.setProperty(bean, "longProperty", new Integer((int) 123));
        assertEquals((long) 123, ((Long) bean.get("longProperty")).longValue());
        BeanUtils.setProperty(bean, "longProperty", new Long((long) 123));
        assertEquals((long) 123, ((Long) bean.get("longProperty")).longValue());
        BeanUtils.setProperty(bean, "longProperty", new Short((short) 123));
        assertEquals((long) 123, ((Long) bean.get("longProperty")).longValue());

    }


    /**
     * Test copying a null property value.
     */
    public void testCopyPropertyNull() throws Exception {

        bean.set("nullProperty", "non-null value");
        BeanUtils.copyProperty(bean, "nullProperty", null);
        assertNull("nullProperty is null", bean.get("nullProperty"));

    }


    /**
     * Test narrowing and widening conversions on short.
     */
    public void testCopyPropertyShort() throws Exception {

        BeanUtils.setProperty(bean, "shortProperty", new Byte((byte) 123));
        assertEquals((short) 123, ((Short) bean.get("shortProperty")).shortValue());
/*
        BeanUtils.setProperty(bean, "shortProperty", new Double((double) 123));
        assertEquals((short) 123, ((Short) bean.get("shortProperty")).shortValue());
        BeanUtils.setProperty(bean, "shortProperty", new Float((float) 123));
        assertEquals((short) 123, ((Short) bean.get("shortProperty")).shortValue());
*/
        BeanUtils.setProperty(bean, "shortProperty", new Integer((int) 123));
        assertEquals((short) 123, ((Short) bean.get("shortProperty")).shortValue());
        BeanUtils.setProperty(bean, "shortProperty", new Long((long) 123));
        assertEquals((short) 123, ((Short) bean.get("shortProperty")).shortValue());
        BeanUtils.setProperty(bean, "shortProperty", new Short((short) 123));
        assertEquals((short) 123, ((Short) bean.get("shortProperty")).shortValue());

    }


    /**
     * Test copying a property using a nested indexed array expression,
     * with and without conversions.
     */
    public void testCopyPropertyNestedIndexedArray() throws Exception {

        int origArray[] = { 0, 10, 20, 30, 40};
        int intArray[] = { 0, 0, 0 };
        ((TestBean) bean.get("nested")).setIntArray(intArray);
        int intChanged[] = { 0, 0, 0 };

        // No conversion required
        BeanUtils.copyProperty(bean, "nested.intArray[1]", new Integer(1));
        checkIntArray((int[]) bean.get("intArray"), origArray);
        intChanged[1] = 1;
        checkIntArray(((TestBean) bean.get("nested")).getIntArray(),
                      intChanged);

        // Widening conversion required
        BeanUtils.copyProperty(bean, "nested.intArray[1]", new Byte((byte) 2));
        checkIntArray((int[]) bean.get("intArray"), origArray);
        intChanged[1] = 2;
        checkIntArray(((TestBean) bean.get("nested")).getIntArray(),
                      intChanged);

        // Narrowing conversion required
        BeanUtils.copyProperty(bean, "nested.intArray[1]", new Long((long) 3));
        checkIntArray((int[]) bean.get("intArray"), origArray);
        intChanged[1] = 3;
        checkIntArray(((TestBean) bean.get("nested")).getIntArray(),
                      intChanged);

        // String conversion required
        BeanUtils.copyProperty(bean, "nested.intArray[1]", "4");
        checkIntArray((int[]) bean.get("intArray"), origArray);
        intChanged[1] = 4;
        checkIntArray(((TestBean) bean.get("nested")).getIntArray(),
                      intChanged);

    }


    /**
     * Test copying a property using a nested mapped map property.
     */
    public void testCopyPropertyNestedMappedMap() throws Exception {

        Map origMap = new HashMap();
        origMap.put("First Key", "First Value");
        origMap.put("Second Key", "Second Value");
        Map changedMap = new HashMap();
        changedMap.put("First Key", "First Value");
        changedMap.put("Second Key", "Second Value");

        // No conversion required
        BeanUtils.copyProperty(bean, "nested.mapProperty(Second Key)",
                               "New Second Value");
        checkMap((Map) bean.get("mapProperty"), origMap);
        changedMap.put("Second Key", "New Second Value");
        checkMap(((TestBean) bean.get("nested")).getMapProperty(), changedMap);

    }


    /**
     * Test copying a property using a nested simple expression, with and
     * without conversions.
     */
    public void testCopyPropertyNestedSimple() throws Exception {

        bean.set("intProperty", new Integer(0));
        nested.setIntProperty(0);

        // No conversion required
        BeanUtils.copyProperty(bean, "nested.intProperty", new Integer(1));
        assertEquals(0, ((Integer) bean.get("intProperty")).intValue());
        assertEquals(1, nested.getIntProperty());

        // Widening conversion required
        BeanUtils.copyProperty(bean, "nested.intProperty", new Byte((byte) 2));
        assertEquals(0, ((Integer) bean.get("intProperty")).intValue());
        assertEquals(2, nested.getIntProperty());

        // Narrowing conversion required
        BeanUtils.copyProperty(bean, "nested.intProperty", new Long((long) 3));
        assertEquals(0, ((Integer) bean.get("intProperty")).intValue());
        assertEquals(3, nested.getIntProperty());

        // String conversion required
        BeanUtils.copyProperty(bean, "nested.intProperty", "4");
        assertEquals(0, ((Integer) bean.get("intProperty")).intValue());
        assertEquals(4, nested.getIntProperty());

    }


    // ------------------------------------------------------ Protected Methods


    // Ensure that the nested intArray matches the specified values
    protected void checkIntArray(int actual[], int expected[]) {
        assertNotNull("actual array not null", actual);
        assertEquals("actual array length", expected.length, actual.length);
        for (int i = 0; i < actual.length; i++) {
            assertEquals("actual array value[" + i + "]",
                         expected[i], actual[i]);
        }
    }


    // Ensure that the actual Map matches the expected Map
    protected void checkMap(Map actual, Map expected) {
        assertNotNull("actual map not null", actual);
        assertEquals("actual map size", expected.size(), actual.size());
        Iterator keys = expected.keySet().iterator();
        while (keys.hasNext()) {
            Object key = keys.next();
            assertEquals("actual map value(" + key + ")",
                         expected.get(key), actual.get(key));
        }
    }


    /**
     * Create and return a <code>DynaClass</code> instance for our test
     * <code>DynaBean</code>.
     */
    protected static DynaClass createDynaClass() {

        int intArray[] = new int[0];
        String stringArray[] = new String[0];

        DynaClass dynaClass = new BasicDynaClass
                ("TestDynaClass", null,
                        new DynaProperty[]{
                            new DynaProperty("booleanProperty", Boolean.TYPE),
                            new DynaProperty("booleanSecond", Boolean.TYPE),
                            new DynaProperty("byteProperty", Byte.TYPE),
                            new DynaProperty("doubleProperty", Double.TYPE),
                            new DynaProperty("dupProperty", stringArray.getClass()),
                            new DynaProperty("floatProperty", Float.TYPE),
                            new DynaProperty("intArray", intArray.getClass()),
                            new DynaProperty("intIndexed", intArray.getClass()),
                            new DynaProperty("intProperty", Integer.TYPE),
                            new DynaProperty("listIndexed", List.class),
                            new DynaProperty("longProperty", Long.TYPE),
                            new DynaProperty("mapProperty", Map.class),
                            new DynaProperty("mappedProperty", Map.class),
                            new DynaProperty("mappedIntProperty", Map.class),
                            new DynaProperty("nested", TestBean.class),
                            new DynaProperty("nullProperty", String.class),
                            new DynaProperty("shortProperty", Short.TYPE),
                            new DynaProperty("stringArray", stringArray.getClass()),
                            new DynaProperty("stringIndexed", stringArray.getClass()),
                            new DynaProperty("stringProperty", String.class),
                        });
        return (dynaClass);

    }


}
