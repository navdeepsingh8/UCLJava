// mappings of alternative forms of opcodes

public interface MappingsIF
{
    class mapping {
        String 
            synonym,
            opcode;

        mapping(String s, String t) 
            {synonym = s; opcode = t;}
    }

    mapping map[] = 
    {
        new mapping("div", "/"),
        new mapping("mod", "%"),
        new mapping("=", "=="),
        new mapping("!=", "<>"),
        new mapping("++", "inc"),
        new mapping("-", "dec"),
    };
    

}

