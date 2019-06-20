package com.vortex.io;

import java.io.*;

public class SerializeObject {

    public static Object deserialize(InputStream inputStream) throws IOException, ClassNotFoundException {
        ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
        return objectInputStream.readObject();
    }

    public static void serialize(OutputStream outputStream, Object obj) throws IOException {
        ObjectOutputStream stream = new ObjectOutputStream(outputStream);
        stream.writeObject(obj);
    }

    static class A { // can't serialize because it doesn't implement Serializable interface
    }

    static class B implements Serializable {
        private final String name;

        public B(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        @Override
        public String toString() {
            return "B{name='" + name + '\'' + '}';
        }
    }

    static class C extends B { // can't serialize

        public C(String name) {
            super(name);
        }

        private void writeObject(ObjectOutputStream oos) throws NotSerializableException {
            throw new NotSerializableException();
        }
    }

    static class D extends B { // can't deserialize

        public D(String name) {
            super(name);
        }

        private void readObject(ObjectInputStream oos) throws IOException {
            throw new NotSerializableException();
        }
    }
}
