import * as React from 'react';
import styled from 'styled-components';

export interface GlobalNavBarProps {}

const GlobalNavBarBlock = styled.div`
  border: none;
  outline: none;
  box-shadow: 0px 2px 4px -1px rgba(0, 0, 0, 0.2),
    0px 4px 5px 0px rgba(0, 0, 0, 0.14), 0px 1px 10px 0px rgba(0, 0, 0, 0.12);
  .typography {
    font-size: 1.25rem;
    color: #ffffff;
  }
  display: flex;
  padding: 1.5rem;
  background: #283593;
`;
export default class GlobalNavBar extends React.Component<
  GlobalNavBarProps,
  any
> {
  public render() {
    return (
      <GlobalNavBarBlock>
        <div className="typography">Event</div>
      </GlobalNavBarBlock>
    );
  }
}
