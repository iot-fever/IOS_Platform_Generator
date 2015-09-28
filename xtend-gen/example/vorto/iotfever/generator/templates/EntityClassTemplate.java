package example.vorto.iotfever.generator.templates;

import org.eclipse.emf.common.util.EList;
import org.eclipse.vorto.codegen.api.tasks.IFileTemplate;
import org.eclipse.vorto.core.api.model.datatype.Entity;
import org.eclipse.vorto.core.api.model.datatype.ObjectPropertyType;
import org.eclipse.vorto.core.api.model.datatype.PrimitivePropertyType;
import org.eclipse.vorto.core.api.model.datatype.PrimitiveType;
import org.eclipse.vorto.core.api.model.datatype.Property;
import org.eclipse.vorto.core.api.model.datatype.PropertyType;
import org.eclipse.vorto.core.api.model.datatype.Type;
import org.eclipse.xtend2.lib.StringConcatenation;

@SuppressWarnings("all")
public class EntityClassTemplate implements IFileTemplate<Entity> {
  @Override
  public String getFileName(final Entity context) {
    String _name = context.getName();
    return (_name + ".swift");
  }
  
  @Override
  public String getPath(final Entity context) {
    return "src-gen/";
  }
  
  @Override
  public String getContent(final Entity context) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("//Generated by Vorto");
    _builder.newLine();
    _builder.newLine();
    _builder.append("import Foundation");
    _builder.newLine();
    _builder.newLine();
    _builder.append("class ");
    String _name = context.getName();
    _builder.append(_name, "");
    _builder.append(" {");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    {
      EList<Property> _properties = context.getProperties();
      for(final Property property : _properties) {
        _builder.append("\t");
        _builder.append("var ");
        String _name_1 = property.getName();
        _builder.append(_name_1, "\t");
        _builder.append(" : ");
        {
          boolean _isMultiplicity = property.isMultiplicity();
          if (_isMultiplicity) {
            _builder.append("[");
            PropertyType _type = property.getType();
            String _type_1 = this.getType(_type);
            _builder.append(_type_1, "\t");
            _builder.append("]");
          } else {
            PropertyType _type_2 = property.getType();
            String _type_3 = this.getType(_type_2);
            _builder.append(_type_3, "\t");
          }
        }
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.append("\t");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("init() {}");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    return _builder.toString();
  }
  
  public String getType(final PropertyType propType) {
    if ((propType instanceof PrimitivePropertyType)) {
      PrimitivePropertyType primitiveProp = ((PrimitivePropertyType) propType);
      PrimitiveType _type = primitiveProp.getType();
      if (_type != null) {
        switch (_type) {
          case STRING:
            return "String";
          case INT:
            return "Int";
          case DATETIME:
            return "NSDate";
          case DOUBLE:
            return "Double";
          case FLOAT:
            return "Float";
          default:
            return "String";
        }
      } else {
        return "String";
      }
    } else {
      Type _type_1 = ((ObjectPropertyType) propType).getType();
      return _type_1.getName();
    }
  }
}
