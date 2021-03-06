package example.vorto.iotfever.generator.templates

import org.eclipse.vorto.codegen.api.tasks.IFileTemplate
import org.eclipse.vorto.core.api.model.informationmodel.InformationModel
import org.eclipse.vorto.core.api.model.functionblock.ReturnObjectType

class DeviceServiceTemplate implements IFileTemplate<InformationModel> {

	override getFileName(InformationModel context) {
		return context.name+"Device.swift"
	}
	
	override getPath(InformationModel context) {
		return "src-gen/"
	}
	
	override getContent(InformationModel context) {
'''
//Generated by Vorto

import Foundation
import CoreBluetooth

let deviceName = "«context.displayname»"

let «context.name»InfoServiceUUID = CBUUID(string: "add uuid here") //TODO
«FOR fbProperty : context.properties»
let «fbProperty.type.name»ServiceUUID = CBUUID(string: "add uuid of «fbProperty.name» here") //TODO
«ENDFOR»

// Characteristic UUIDs
let «context.name»InfoSystemIDUUID  = CBUUID(string: "add uuid here") //TODO
«FOR fbProperty : context.properties»
let «fbProperty.type.name»DataUUID = CBUUID(string: "add uuid of «fbProperty.name» for data here")
«IF fbProperty.type.functionblock.configuration != null»
let «fbProperty.type.name»ConfigUUID = CBUUID(string: "add uuid of «fbProperty.name» for configuration here")
«ENDIF»
«ENDFOR»

class «context.name»Device {
    
    // Check name of device from advertisement data
    class func found (advertisementData: [NSObject : AnyObject]!) -> Bool {
        let nameOfDeviceFound = (advertisementData as NSDictionary).objectForKey(CBAdvertisementDataLocalNameKey) as? NSString
        
        return (nameOfDeviceFound == deviceName)
    }
    
    // Check if the service has a valid UUID
    class func validService (service : CBService) -> Bool {
        if service.UUID == «context.name»InfoServiceUUID
        	«FOR fbProperty : context.properties»
        	|| service.UUID == «fbProperty.type.name»ServiceUUID
        	«ENDFOR» {
                return true
        }
        else {
            return false
        }
    }
    
    // Check if the characteristic has a valid data UUID
    class func validDataCharacteristic (characteristic : CBCharacteristic) -> Bool {
        if service.UUID == «context.name»InfoServiceUUID
        	«FOR fbProperty : context.properties»
        	|| service.UUID == «fbProperty.type.name»ServiceUUID
        	«ENDFOR» {
                return true
        }
        else {
            return false
        }
    }
    
    «FOR fbProperty : context.properties»
    	«FOR operation : fbProperty.type.functionblock.operations»
    	«IF operation.returnType != null»
    	class func «operation.name»(value : NSData) -> «(operation.returnType as ReturnObjectType).returnType.name» {
    		var result = «(operation.returnType as ReturnObjectType).returnType.name»()
    		//TODO convert and map value to response type
    		return result
    	}
    	«ENDIF»
    	«ENDFOR»
    «ENDFOR»
    
'''
	}
}