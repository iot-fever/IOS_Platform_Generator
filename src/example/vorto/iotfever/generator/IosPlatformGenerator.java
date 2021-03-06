package example.vorto.iotfever.generator;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.vorto.codegen.api.ICodeGenerator;
import org.eclipse.vorto.codegen.api.mapping.IMappingAware;
import org.eclipse.vorto.codegen.api.tasks.DatatypeGeneratorTask;
import org.eclipse.vorto.codegen.api.tasks.eclipse.EclipseProjectGenerator;
import org.eclipse.vorto.codegen.ui.display.MessageDisplayFactory;
import org.eclipse.vorto.core.api.model.informationmodel.InformationModel;
import org.eclipse.vorto.core.api.model.mapping.StereoTypeTarget;
import org.eclipse.vorto.core.model.IMapping;
import org.eclipse.vorto.core.model.IModelProject;
import org.eclipse.vorto.core.model.ModelId;
import org.eclipse.vorto.core.model.ModelIdFactory;
import org.eclipse.vorto.core.service.ModelProjectServiceFactory;

import example.vorto.iotfever.generator.templates.CoreBluetoothDetectionTemplate;
import example.vorto.iotfever.generator.templates.DeviceServiceTemplate;
import example.vorto.iotfever.generator.templates.EntityClassTemplate;
import example.vorto.iotfever.generator.templates.EnumClassTemplate;

public class IosPlatformGenerator implements ICodeGenerator<InformationModel>, IMappingAware {

	private IMapping mapping = null;
	
	@Override
	public String getTargetPlatform() {
		return "ios";
	}

	@Override
	public void setMapping(IMapping mapping) {
		this.mapping = mapping;
		
	}

	@Override
	public void generate(final InformationModel ctx, IProgressMonitor monitor) {
		final IModelProject currentProject = getSelectedModelProject(ctx);
		String outputProjectName = currentProject.getProject().getName();
		EclipseProjectGenerator<InformationModel> generator = new EclipseProjectGenerator<InformationModel>(outputProjectName);
		generator.addTask(new DatatypeGeneratorTask(new EntityClassTemplate(), new EnumClassTemplate()));
		
		if (mapping.getAllRules().isEmpty()) {
			MessageDisplayFactory.getMessageDisplay().displayError("This generator requires a mapping with target 'ios'");
			return;
		}
		
		StereoTypeTarget target = (StereoTypeTarget)mapping.getAllRules().get(0).getTarget();
		
		if ("ble".equalsIgnoreCase(target.getName())) {
			generator.addTask(new CoreBluetoothDetectionTemplate());
			generator.addTask(new DeviceServiceTemplate());
		} else {
			MessageDisplayFactory.getMessageDisplay().displayWarning(target.getName()+" is not supported at the moment. Please use 'BLE' for binding device to bluetooth.");
			return;
		}
		
		generator.generate(ctx, monitor);
	}
	
	private IModelProject getSelectedModelProject(final InformationModel infomodel) {
		final ModelId modelId = ModelIdFactory.newInstance(infomodel);
		return ModelProjectServiceFactory.getDefault().getProjectByModelId(modelId);
	}

	@Override
	public String getName() {
		return "IOS Platform Generator";
	}

}
