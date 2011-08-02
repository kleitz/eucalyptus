package com.eucalyptus.cluster.callback;

import java.util.List;
import java.util.NoSuchElementException;
import org.apache.log4j.Logger;
import com.eucalyptus.auth.Accounts;
import com.eucalyptus.auth.principal.AccountFullName;
import com.eucalyptus.auth.principal.UserFullName;
import com.eucalyptus.cluster.Cluster;
import com.eucalyptus.cluster.Clusters;
import com.eucalyptus.cluster.NetworkAlreadyExistsException;
import com.eucalyptus.network.Network;
import com.eucalyptus.network.NetworkGroupUtil;
import com.eucalyptus.network.NetworkGroups;
import com.eucalyptus.network.NetworkRulesGroup;
import com.eucalyptus.network.NetworkToken;
import com.eucalyptus.network.Networks;
import com.eucalyptus.network.PrivateNetworkIndices;
import com.eucalyptus.util.Internets;
import com.eucalyptus.util.async.FailedRequestException;
import com.google.common.collect.Lists;
import edu.ucsb.eucalyptus.msgs.DescribeNetworksResponseType;
import edu.ucsb.eucalyptus.msgs.DescribeNetworksType;
import edu.ucsb.eucalyptus.msgs.NetworkInfoType;

public class NetworkStateCallback extends StateUpdateMessageCallback<Cluster, DescribeNetworksType, DescribeNetworksResponseType> {
  private static Logger LOG = Logger.getLogger( NetworkStateCallback.class );
  
  public NetworkStateCallback( ) {
    super( new DescribeNetworksType( ) {
      {
        regarding( );
        setClusterControllers( Lists.newArrayList( Clusters.getInstance( ).getClusterAddresses( ) ) );
        setNameserver( Internets.localHostAddress( ) );
      }
    } );
  }
  
  /**
   * @see com.eucalyptus.util.async.MessageCallback#fire(edu.ucsb.eucalyptus.msgs.BaseMessage)
   * @param reply
   */
  @Override
  public void fire( DescribeNetworksResponseType reply ) {
    NetworkGroups.update( reply.getUseVlans( ), reply.getAddrsPerNet( ), reply.getAddrIndexMax( ), reply.getAddrIndexMax( ), reply.getActiveNetworks( ) );    
  }
  
  /**
   * @see com.eucalyptus.cluster.callback.StateUpdateMessageCallback#fireException(com.eucalyptus.util.async.FailedRequestException)
   * @param t
   */
  @Override
  public void fireException( FailedRequestException t ) {
    LOG.debug( "Request to " + this.getSubject( ).getName( ) + " failed: " + t.getMessage( ) );
  }
}