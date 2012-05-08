/* $Name:  $ */
/* $Id: oblit_anon.js,v 1.2 2010/10/27 19:24:56 ajokela Exp $ */
/** the options */
opt = Opt.get(
	arguments, 
	{
	 d: "directory",
	 c: "conf",
	 "D[]": "define"
	}
);

/** configuration */
opt.conf = {
	/** keep */
	keep: true,
	/** base */
	base: getBase(this, {p: properties})
}



