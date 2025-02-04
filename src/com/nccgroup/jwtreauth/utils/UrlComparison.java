/*
Copyright 2022 NCC Group
Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at
    https://www.apache.org/licenses/LICENSE-2.0
Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/

package com.nccgroup.jwtreauth.utils;

import javax.validation.constraints.NotNull;

import java.net.URL;

public class UrlComparison {
    /**
     * The conditions for equality of URLs are defined below:
     * - getPort (if -1 then replace with the default port for the given protocol)
     * - getPath
     * - gethost
     * - getProtocol
     * <p>
     * These methods must all return equal values.
     * The comparison is done in the order defined above, with the method exiting early upon a false comparison.
     *
     * @param a the first URL to compare
     * @param b the second URL to compare
     * @return whether the two URL objects are equal
     */
    public static boolean compareEqual(final @NotNull URL a, final @NotNull URL b) {
        boolean portsEqual = getPortOrDefault(a) == getPortOrDefault(b);
        if (!portsEqual) return false;

        boolean pathsEqual = a.getPath().equals(b.getPath());
        if (!pathsEqual) return false;

        // getHost can return null and therefore must be checked for the null case
        var aHost = a.getHost();
        var bHost = b.getHost();
        if (aHost == null ^ bHost == null) return false;
        if (aHost != null && !aHost.equals(bHost)) return false;

        return a.getProtocol().equals(b.getProtocol());
    }

    /**
     * Gets a port number from a URL object, if the number returned is -1
     * i.e. if there isn't a port number specified in the request.
     * Then an attempt is made to look up the default port number for that protocol.
     *
     * @param url the URL object to get the port for
     * @return the port number of the given URL
     */
    private static int getPortOrDefault(@NotNull URL url) {
        int port = url.getPort();
        if (port == -1) {
            return url.getDefaultPort();
        } else {
            return port;
        }
    }
}
