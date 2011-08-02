/*******************************************************************************
 * Copyright (c) 2009  Eucalyptus Systems, Inc.
 * 
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, only version 3 of the License.
 * 
 * 
 *  This file is distributed in the hope that it will be useful, but WITHOUT
 *  ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 *  FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 *  for more details.
 * 
 *  You should have received a copy of the GNU General Public License along
 *  with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 *  Please contact Eucalyptus Systems, Inc., 130 Castilian
 *  Dr., Goleta, CA 93101 USA or visit <http://www.eucalyptus.com/licenses/>
 *  if you need additional information or have any questions.
 * 
 *  This file may incorporate work covered under the following copyright and
 *  permission notice:
 * 
 *    Software License Agreement (BSD License)
 * 
 *    Copyright (c) 2008, Regents of the University of California
 *    All rights reserved.
 * 
 *    Redistribution and use of this software in source and binary forms, with
 *    or without modification, are permitted provided that the following
 *    conditions are met:
 * 
 *      Redistributions of source code must retain the above copyright notice,
 *      this list of conditions and the following disclaimer.
 * 
 *      Redistributions in binary form must reproduce the above copyright
 *      notice, this list of conditions and the following disclaimer in the
 *      documentation and/or other materials provided with the distribution.
 * 
 *    THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 *    IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED
 *    TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A
 *    PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER
 *    OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 *    EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 *    PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 *    PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 *    LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 *    NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 *    SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE. USERS OF
 *    THIS SOFTWARE ACKNOWLEDGE THE POSSIBLE PRESENCE OF OTHER OPEN SOURCE
 *    LICENSED MATERIAL, COPYRIGHTED MATERIAL OR PATENTED MATERIAL IN THIS
 *    SOFTWARE, AND IF ANY SUCH MATERIAL IS DISCOVERED THE PARTY DISCOVERING
 *    IT MAY INFORM DR. RICH WOLSKI AT THE UNIVERSITY OF CALIFORNIA, SANTA
 *    BARBARA WHO WILL THEN ASCERTAIN THE MOST APPROPRIATE REMEDY, WHICH IN
 *    THE REGENTS' DISCRETION MAY INCLUDE, WITHOUT LIMITATION, REPLACEMENT
 *    OF THE CODE SO IDENTIFIED, LICENSING OF THE CODE SO IDENTIFIED, OR
 *    WITHDRAWAL OF THE CODE CAPABILITY TO THE EXTENT NEEDED TO COMPLY WITH
 *    ANY SUCH LICENSES OR RIGHTS.
 *******************************************************************************
 * @author chris grzegorczyk <grze@eucalyptus.com>
 */

package edu.ucsb.eucalyptus.cloud;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import com.eucalyptus.auth.principal.UserFullName;
import com.eucalyptus.network.NetworkToken;

public class ResourceToken implements Comparable {
  private final String       cluster;
  private final String       correlationId;
  private final UserFullName userFullName;
  private List<String>       instanceIds   = new ArrayList<String>( );
  private List<String>       instanceUuids = new ArrayList<String>( );
  private List<String>       addresses     = new ArrayList<String>( );
  private List<NetworkToken> networkTokens = new ArrayList<NetworkToken>( );
  private final Integer      amount;
  private final String       vmType;
  private final Date         creationTime;
  private final Integer      sequenceNumber;
  
  public ResourceToken( final UserFullName userFullName, final String correlationId, final String cluster, final int amount, final int sequenceNumber,
                        final String vmType ) {
    this.cluster = cluster;
    this.correlationId = correlationId;
    this.userFullName = userFullName;
    this.amount = amount;
    for( int i = 0; i < amount; i ++ ) {
      this.instanceUuids.add( UUID.randomUUID( ).toString( ) );
    }
    this.sequenceNumber = sequenceNumber;
    this.creationTime = Calendar.getInstance( ).getTime( );
    this.vmType = vmType;
  }
  
  public NetworkToken getPrimaryNetwork( ) {
    return this.networkTokens.size( ) > 0
      ? this.networkTokens.get( 0 )
      : null;
  }
  
  public String getCluster( ) {
    return this.cluster;
  }
  
  public String getCorrelationId( ) {
    return this.correlationId;
  }
  
  public UserFullName getUserFullName( ) {
    return this.userFullName;
  }
  
  public List<String> getInstanceIds( ) {
    return this.instanceIds;
  }
  
  public List<String> getAddresses( ) {
    return this.addresses;
  }
  
  public List<NetworkToken> getNetworkTokens( ) {
    return this.networkTokens;
  }
  
  public Integer getAmount( ) {
    return this.amount;
  }
  
  public String getVmType( ) {
    return this.vmType;
  }
  
  public Date getCreationTime( ) {
    return this.creationTime;
  }
  
  public Integer getSequenceNumber( ) {
    return this.sequenceNumber;
  }
  
  @Override
  public boolean equals( final Object o ) {
    if ( this == o ) return true;
    if ( !( o instanceof ResourceToken ) ) return false;
    
    ResourceToken that = ( ResourceToken ) o;
    
    if ( !amount.equals( that.amount ) ) return false;
    if ( !cluster.equals( that.cluster ) ) return false;
    if ( !correlationId.equals( that.correlationId ) ) return false;
    if ( !creationTime.equals( that.creationTime ) ) return false;
    
    return true;
  }
  
  @Override
  public int hashCode( ) {
    int result = cluster.hashCode( );
    result = 31 * result + correlationId.hashCode( );
    result = 31 * result + amount;
    result = 31 * result + creationTime.hashCode( );
    return result;
  }
  
  @Override
  public int compareTo( final Object o ) {
    ResourceToken that = ( ResourceToken ) o;
    return this.sequenceNumber - that.sequenceNumber;
  }
  
  public List<String> getInstanceUuids( ) {
    return this.instanceUuids;
  }
  
}
