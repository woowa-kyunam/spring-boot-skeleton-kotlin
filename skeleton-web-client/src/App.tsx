import * as React from 'react';
import './App.css';
import GlobalNavBar from './component/common/GlobalNavBar';
import EventPage from './component/event/EventPage';
import { Route } from 'react-router-dom';

class App extends React.Component {
  public render() {
    return (
      <>
        <GlobalNavBar />
        <Route path="/:category?" component={EventPage} />
      </>
    );
  }
}

export default App;
