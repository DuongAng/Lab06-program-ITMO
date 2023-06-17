package utility;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import data.Person;

import java.util.HashMap;
import java.util.Map;


public class HashMapConverter implements Converter {

    @Override
    public void marshal(Object source, HierarchicalStreamWriter writer, MarshallingContext context) {
        Map<Integer, Person> map = (Map<Integer, Person>) source;
        for (Map.Entry<Integer, Person> entry : map.entrySet()) {
            writer.startNode("person");
            writer.addAttribute("id", entry.getKey().toString());
            context.convertAnother(entry.getValue());
            writer.endNode();
        }
    }

    @Override
    public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
        Map<Integer, Person> map = new HashMap<>();
        while (reader.hasMoreChildren()) {
            reader.moveDown();
            Integer id = Integer.parseInt(reader.getAttribute("id"));
            Person person = (Person) context.convertAnother(null, Person.class);
            map.put(id, person);
            reader.moveUp();
        }
        return map;
    }

    @Override
    public boolean canConvert(Class type) {
        return Map.class.isAssignableFrom(type);
    }
}
