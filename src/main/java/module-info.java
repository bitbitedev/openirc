/**
 * The module-info.java file for the OpenIRC library.
 */
module dev.bitbite.openirc {
	exports dev.bitbite.openirc;
	exports dev.bitbite.openirc.exceptions;
	
	requires dev.thatsnasu.ansi;
	requires dev.bitbite.opennetlib;
	requires io.github.classgraph;
}