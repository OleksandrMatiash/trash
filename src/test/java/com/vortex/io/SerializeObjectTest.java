package com.vortex.io;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.NotSerializableException;

import static com.vortex.io.SerializeObject.deserialize;
import static com.vortex.io.SerializeObject.serialize;
import static org.junit.Assert.*;

public class SerializeObjectTest {

    @Test(expected = NotSerializableException.class)
    public void serialize_notSerializable() throws IOException {
        serialize(new ByteArrayOutputStream(100), new SerializeObject.A());
    }

    @Test
    public void serializeAndDeserialize() throws IOException, ClassNotFoundException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(100);
        Object orig = new SerializeObject.B("42");

        serialize(byteArrayOutputStream, orig);

        SerializeObject.B obj = (SerializeObject.B) deserialize(new ByteArrayInputStream(byteArrayOutputStream.toByteArray()));

        assertNotSame(orig, obj);
        assertEquals("42", obj.getName());
    }

    @Test(expected = NotSerializableException.class)
    public void serialize_classExtendsSerializableClassButDisallowSerialization() throws IOException, ClassNotFoundException {
        serialize(new ByteArrayOutputStream(100), new SerializeObject.C("42"));
    }

    @Test(expected = NotSerializableException.class)
    public void serializeAndDeserialize_classExtendsSerializableClassButDisallowDeserialization() throws IOException, ClassNotFoundException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(100);
        Object orig = new SerializeObject.D("42");
        try {
            serialize(byteArrayOutputStream, orig);
        } catch (NotSerializableException ex) {
            fail();
        }

        SerializeObject.D obj = (SerializeObject.D) deserialize(new ByteArrayInputStream(byteArrayOutputStream.toByteArray()));
    }

}