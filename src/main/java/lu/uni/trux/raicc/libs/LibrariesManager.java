package lu.uni.trux.raicc.libs;

import lu.uni.trux.raicc.utils.Constants;

/*-
 * #%L
 * RAICC
 * 
 * %%
 * Copyright (C) 2020 Jordan Samhi
 * University of Luxembourg - Interdisciplinary Centre for
 * Security Reliability and Trust (SnT) - TruX - All rights reserved
 *
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 2.1 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Lesser Public License for more details.
 * 
 * You should have received a copy of the GNU General Lesser Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/lgpl-2.1.html>.
 * #L%
 */

public class LibrariesManager extends FileLoader {
	
	private static LibrariesManager instance;
	
	private LibrariesManager () {
		super();
	}

	public static LibrariesManager v() {
		if(instance == null) {
			instance = new LibrariesManager();
		}
		return instance;
	}

	@Override
	public boolean contains(String s) {
		for(String lib : this.items) {
			if(s.startsWith(lib)) {
				return true;
			}
		}
		return false;
	}

	@Override
	protected String getFile() {
		return Constants.LIBRARIES_FILE;
	}
}
