package com.hzth.myapp.javaAgent;

import java.util.List;

import com.sun.tools.attach.VirtualMachine;
import com.sun.tools.attach.VirtualMachineDescriptor;

public class AttachUtil {

	public static void main(String[] args) throws Exception {
		List<VirtualMachineDescriptor> virtualMachineDescriptors = VirtualMachine.list();
		for (VirtualMachineDescriptor desc : virtualMachineDescriptors) {
			// VirtualMachine vm = VirtualMachine.attach(desc);
			// System.out.println(vm.id());
			// vm.detach();
		}
		VirtualMachine vm = VirtualMachine.attach("2344");
		vm.loadAgent("C:/Users/tianyl/git/mywebapp/src/main/java/com/hzth/myapp/javaAgent/agent2.jar");
		vm.detach();
	}
}
