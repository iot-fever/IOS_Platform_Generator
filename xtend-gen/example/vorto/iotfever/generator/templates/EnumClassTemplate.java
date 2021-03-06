package example.vorto.iotfever.generator.templates;

import org.eclipse.emf.common.util.EList;
import org.eclipse.vorto.codegen.api.tasks.IFileTemplate;
import org.eclipse.vorto.core.api.model.datatype.EnumLiteral;
import org.eclipse.xtend2.lib.StringConcatenation;

@SuppressWarnings("all")
public class EnumClassTemplate implements IFileTemplate<org.eclipse.vorto.core.api.model.datatype.Enum> {
  @Override
  public String getFileName(final org.eclipse.vorto.core.api.model.datatype.Enum context) {
    String _name = context.getName();
    return (_name + ".swift");
  }
  
  @Override
  public String getPath(final org.eclipse.vorto.core.api.model.datatype.Enum context) {
    return "src-gen/";
  }
  
  @Override
  public String getContent(final org.eclipse.vorto.core.api.model.datatype.Enum context) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("//Generated by Vorto");
    _builder.newLine();
    _builder.newLine();
    _builder.append("import Foundation");
    _builder.newLine();
    _builder.newLine();
    _builder.append("enum ");
    String _name = context.getName();
    _builder.append(_name, "");
    _builder.append(" {");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    {
      EList<EnumLiteral> _enums = context.getEnums();
      for(final EnumLiteral literal : _enums) {
        _builder.append("\t");
        _builder.append("case ");
        String _name_1 = literal.getName();
        _builder.append(_name_1, "\t");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.append("}");
    _builder.newLine();
    return _builder.toString();
  }
}
